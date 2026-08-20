package CodeGenerator;

import AST.ASTNode;
import AST.parser_pkg.Program;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

/** Writes the complete Python AST without flattening child nodes. */
public final class AstPythonWriter {

    public void write(
            Program program,
            Map<String, Object> contextData,
            Map<String, Map<String, Object>> templateContexts,
            Path output
    ) throws IOException {
        if (program == null) {
            throw new IllegalArgumentException("Cannot write a null Python AST.");
        }

        Files.createDirectories(output.getParent());
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        appendNodeFields(program, json, 1);
        json.append(",\n  \"contextData\": ");
        appendValue(contextData, json, 1);
        json.append(",\n  \"templateContexts\": ");
        appendValue(templateContexts, json, 1);
        json.append("\n}\n");

        Files.write(output, json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private void appendNodeFields(ASTNode node, StringBuilder json, int depth) {
        String indent = indent(depth);
        json.append(indent).append("\"type\": ");
        appendString(node.getClass().getSimpleName(), json);
        json.append(",\n").append(indent).append("\"line\": ")
                .append(node.getLineNumber());

        if (!node.nodeDetails().isBlank()) {
            json.append(",\n").append(indent).append("\"detail\": ");
            appendString(node.nodeDetails(), json);
        }

        json.append(",\n").append(indent).append("\"children\": [");
        if (!node.childNodes().isEmpty()) json.append('\n');
        for (int index = 0; index < node.childNodes().size(); index++) {
            ASTNode child = node.childNodes().get(index);
            json.append(indent(depth + 1)).append("{\n");
            appendNodeFields(child, json, depth + 2);
            json.append('\n').append(indent(depth + 1)).append('}');
            if (index + 1 < node.childNodes().size()) json.append(',');
            json.append('\n');
        }
        json.append(indent).append(']');
    }

    private void appendValue(Object value, StringBuilder json, int depth) {
        if (value == null) {
            json.append("null");
        } else if (value instanceof String || value instanceof Character) {
            appendString(String.valueOf(value), json);
        } else if (value instanceof Number || value instanceof Boolean) {
            json.append(value);
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            json.append('{');
            if (!map.isEmpty()) json.append('\n');
            int index = 0;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                json.append(indent(depth + 1));
                appendString(String.valueOf(entry.getKey()), json);
                json.append(": ");
                appendValue(entry.getValue(), json, depth + 1);
                if (++index < map.size()) json.append(',');
                json.append('\n');
            }
            json.append(indent(depth)).append('}');
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            json.append('[');
            if (!collection.isEmpty()) json.append('\n');
            int index = 0;
            for (Object item : collection) {
                json.append(indent(depth + 1));
                appendValue(item, json, depth + 1);
                if (++index < collection.size()) json.append(',');
                json.append('\n');
            }
            json.append(indent(depth)).append(']');
        } else {
            appendString(String.valueOf(value), json);
        }
    }

    static void appendString(String value, StringBuilder json) {
        json.append('"');
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"': json.append("\\\""); break;
                case '\\': json.append("\\\\"); break;
                case '\b': json.append("\\b"); break;
                case '\f': json.append("\\f"); break;
                case '\n': json.append("\\n"); break;
                case '\r': json.append("\\r"); break;
                case '\t': json.append("\\t"); break;
                default:
                    if (character < 0x20) {
                        json.append(String.format("\\u%04x", (int) character));
                    } else {
                        json.append(character);
                    }
            }
        }
        json.append('"');
    }

    static String indent(int depth) {
        return "  ".repeat(Math.max(0, depth));
    }
}
