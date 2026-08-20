package CodeGenerator;

import AST.Web.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/** Writes nested Web/Jinja AST reports from real AST nodes. */
public final class AstJinjaWriter {

    public void writeAll(Map<String, HtmlDocument> templates, Path outputDirectory)
            throws IOException {
        Files.createDirectories(outputDirectory);
        removeOldReports(outputDirectory);

        Map<String, HtmlDocument> ordered = new LinkedHashMap<>(templates);
        for (Map.Entry<String, HtmlDocument> entry : ordered.entrySet()) {
            String baseName = entry.getKey().replaceFirst("\\.(jinja|html)$", "");
            writeDocument(
                    entry.getValue(),
                    outputDirectory.resolve("ast_jinja_" + baseName + ".json"));
        }

        StringBuilder aggregate = new StringBuilder();
        aggregate.append("{\n  \"type\": \"JinjaAstCollection\",\n")
                .append("  \"templates\": {");
        if (!ordered.isEmpty()) aggregate.append('\n');

        int index = 0;
        for (Map.Entry<String, HtmlDocument> entry : ordered.entrySet()) {
            aggregate.append("    ");
            AstPythonWriter.appendString(entry.getKey(), aggregate);
            aggregate.append(": ");
            appendNode(entry.getValue(), aggregate, 2);
            if (++index < ordered.size()) aggregate.append(',');
            aggregate.append('\n');
        }
        aggregate.append("  }\n}\n");
        Files.write(
                outputDirectory.resolve("ast_jinja.json"),
                aggregate.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void deleteReports(Path outputDirectory) throws IOException {
        if (Files.isDirectory(outputDirectory)) removeOldReports(outputDirectory);
    }

    private void writeDocument(HtmlDocument document, Path output) throws IOException {
        StringBuilder json = new StringBuilder();
        appendNode(document, json, 0);
        json.append('\n');
        Files.write(output, json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private void removeOldReports(Path outputDirectory) throws IOException {
        try (Stream<Path> files = Files.list(outputDirectory)) {
            for (Path file : files.filter(path -> path.getFileName().toString()
                    .matches("ast_jinja(?:_.*)?\\.json")).toList()) {
                Files.deleteIfExists(file);
            }
        }
    }

    private void appendNode(HtmlNode node, StringBuilder json, int depth) {
        String indent = AstPythonWriter.indent(depth);
        String fieldIndent = AstPythonWriter.indent(depth + 1);
        json.append("{\n").append(fieldIndent).append("\"type\": ");
        AstPythonWriter.appendString(node.getClass().getSimpleName(), json);
        json.append(",\n").append(fieldIndent).append("\"line\": ")
                .append(node.getLine());

        if (node instanceof HtmlDocument) {
            appendNodesField("children", ((HtmlDocument) node).getChildren(), json, depth);
        } else if (node instanceof HtmlElement) {
            HtmlElement element = (HtmlElement) node;
            appendStringField("tag", element.getTagName(), json, depth);
            appendNodesField("attributes", new ArrayList<>(element.getAttributes()), json, depth);
            appendNodesField("children", element.getChildren(), json, depth);
        } else if (node instanceof HtmlSelfClosingElement) {
            HtmlSelfClosingElement element = (HtmlSelfClosingElement) node;
            appendStringField("tag", element.getTagName(), json, depth);
            appendNodesField("attributes", new ArrayList<>(element.getAttributes()), json, depth);
        } else if (node instanceof HtmlAttribute) {
            HtmlAttribute attribute = (HtmlAttribute) node;
            appendStringField("name", attribute.getName(), json, depth);
            appendStringField("value", attribute.getValue(), json, depth);
            appendNodesField("parts", new ArrayList<>(attribute.getParts()), json, depth);
        } else if (node instanceof AttributeText) {
            appendStringField("text", ((AttributeText) node).getText(), json, depth);
        } else if (node instanceof AttributeJinjaExpression) {
            AttributeJinjaExpression part = (AttributeJinjaExpression) node;
            appendNodeField("expression", part.getExpression(), json, depth);
        } else if (node instanceof HtmlText) {
            appendStringField("text", ((HtmlText) node).getText(), json, depth);
        } else if (node instanceof HtmlExpressionBlock) {
            appendStringField("expression",
                    ((HtmlExpressionBlock) node).getExpression(), json, depth);
        } else if (node instanceof HtmlSetStatement) {
            HtmlSetStatement set = (HtmlSetStatement) node;
            appendStringField("variable", set.getVarName(), json, depth);
            appendStringField("expression", set.getExpression(), json, depth);
        } else if (node instanceof HtmlForBlock) {
            HtmlForBlock loop = (HtmlForBlock) node;
            appendStringField("variable", loop.getLoopVar(), json, depth);
            appendStringField("iterable", loop.getIterable(), json, depth);
            appendNodesField("body", loop.getBody(), json, depth);
        } else if (node instanceof HtmlIfBlock) {
            HtmlIfBlock condition = (HtmlIfBlock) node;
            appendStringField("condition", condition.getCondition(), json, depth);
            appendNodesField("thenBranch", condition.getThenBranch(), json, depth);
            appendNodesField("elifBranches",
                    new ArrayList<>(condition.getElifBranches()), json, depth);
            appendNodesField("elseBranch", condition.getElseBranch(), json, depth);
        } else if (node instanceof HtmlElifBranch) {
            HtmlElifBranch branch = (HtmlElifBranch) node;
            appendStringField("condition", branch.getCondition(), json, depth);
            appendNodesField("body", branch.getBody(), json, depth);
        } else if (node instanceof StyleElement) {
            appendNodesField("children",
                    new ArrayList<>(((StyleElement) node).getChildren()), json, depth);
        } else if (node instanceof ScriptElement) {
            ScriptElement script = (ScriptElement) node;
            appendStringField("openingTag", script.getOpeningTag(), json, depth);
            appendStringField("content", script.getContent(), json, depth);
            appendNodesField("children",
                    new ArrayList<>(script.getChildren()), json, depth);
        } else if (node instanceof ScriptText) {
            appendStringField("text", ((ScriptText) node).getText(), json, depth);
        } else if (node instanceof ScriptJinjaExpression) {
            ScriptJinjaExpression expression = (ScriptJinjaExpression) node;
            appendNodeField("expression", expression.getExpression(), json, depth);
        } else {
            appendStringField("value", node.toString(), json, depth);
        }

        json.append('\n').append(indent).append('}');
    }

    private void appendStringField(
            String name,
            String value,
            StringBuilder json,
            int depth
    ) {
        json.append(",\n").append(AstPythonWriter.indent(depth + 1));
        AstPythonWriter.appendString(name, json);
        json.append(": ");
        AstPythonWriter.appendString(value == null ? "" : value, json);
    }

    private void appendNodesField(
            String name,
            List<? extends HtmlNode> nodes,
            StringBuilder json,
            int depth
    ) {
        json.append(",\n").append(AstPythonWriter.indent(depth + 1));
        AstPythonWriter.appendString(name, json);
        json.append(": [");
        if (!nodes.isEmpty()) json.append('\n');
        for (int index = 0; index < nodes.size(); index++) {
            json.append(AstPythonWriter.indent(depth + 2));
            appendNode(nodes.get(index), json, depth + 2);
            if (index + 1 < nodes.size()) json.append(',');
            json.append('\n');
        }
        json.append(AstPythonWriter.indent(depth + 1)).append(']');
    }

    private void appendNodeField(
            String name,
            HtmlNode node,
            StringBuilder json,
            int depth
    ) {
        json.append(",\n").append(AstPythonWriter.indent(depth + 1));
        AstPythonWriter.appendString(name, json);
        json.append(": ");
        if (node == null) {
            json.append("null");
        } else {
            appendNode(node, json, depth + 1);
        }
    }
}
