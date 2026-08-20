package CodeGenerator;

import AST.parser_pkg.*;
import AST.Expression;
import AST.Statement;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.Map;

public class PythonCodeGenerator {

    private final Map<String, Object> contextData = new LinkedHashMap<>();
    private final Map<String, Map<String, Object>>
            templateContexts = new LinkedHashMap<>();
    private final List<String> generationLog = new ArrayList<>();

    private static final String OUTPUT_DIR       = "output" + File.separator;
    private static final String COMPILER_OUT_DIR = "compiler_output" + File.separator;

    public void generate(Program program) {
        generationLog.clear();
        log("=== Starting Python Code Generation ===");
        log("Working directory: " + System.getProperty("user.dir"));

        extractContextData(program);
        log("Extracted " + contextData.size() + " context variable(s) from Python AST.");

        extractTemplateContexts(program);

        log(
                "Extracted template contexts: "
                        + templateContexts
        );

        List<String> templates = findTemplates(program);
        log("Found " + templates.size() + " template(s): " + templates);

        createDirectories();

        copyFile("input" + File.separator + "style.css",  OUTPUT_DIR + "style.css");
        copyFile("input" + File.separator + "script.js",  OUTPUT_DIR + "script.js");
        copyFile("input" + File.separator + "app.py",     OUTPUT_DIR + "app.py");
        copyDirectory(
                "input" + File.separator + "pics",
                OUTPUT_DIR + "pics"
        );

        log("=== Code Generation Completed Successfully ===");
        printSummary(templates);
    }


    private void extractContextData(Program program) {

        contextData.clear();

        for (Statement statement
                : program.getStatements()) {

            if (!(statement instanceof Assignment)) {
                continue;
            }

            Assignment assignment =
                    (Assignment) statement;

            String variableName =
                    assignment.getTarget() != null
                            ? assignment.getTarget().toString()
                            : null;

            if (variableName == null
                    || variableName.equals("app")) {
                continue;
            }

            Object value =
                    extractValue(assignment.getValue());

            if (value != null) {
                contextData.put(
                        variableName,
                        value
                );

                log(
                        "Extracted global variable: "
                                + variableName
                                + " = "
                                + value
                );
            }
        }
    }


    private Object extractValue(Expression expr) {
        if (expr == null) return null;
        if (expr instanceof StringLiteral)  return ((StringLiteral) expr).getValue();
        if (expr instanceof NumberLiteral)  return ((NumberLiteral) expr).getValue();
        if (expr instanceof BooleanLiteral) return ((BooleanLiteral) expr).getValue();
        if (expr instanceof ListExpression) {
            List<Object> list = new ArrayList<>();
            for (Expression elem : ((ListExpression) expr).getElements()) {
                Object val = extractValue(elem);
                if (val != null) list.add(val);
            }
            return list;
        }
        if (expr instanceof DictExpression) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (DictEntry entry : ((DictExpression) expr).getEntries()) {
                String key = extractValue(entry.getKey()) != null
                        ? extractValue(entry.getKey()).toString() : "unknown";
                Object val = extractValue(entry.getValue());
                map.put(key, val);
            }
            return map;
        }
        return null;
    }

    private void extractTemplateContexts(
            Program program
    ) {
        templateContexts.clear();

        for (Statement statement
                : program.getStatements()) {

            if (!(statement instanceof FunctionDef)) {
                continue;
            }

            FunctionDef function =
                    (FunctionDef) statement;

            if (function.body == null) {
                continue;
            }

            Map<String, Object> availableValues =
                    new LinkedHashMap<>(contextData);

            collectTemplateContextsFromBlock(
                    function.body,
                    availableValues
            );
        }
    }

    private void collectTemplateContextsFromBlock(
            Block block,
            Map<String, Object> availableValues
    ) {
        if (block == null) {
            return;
        }

        for (Statement statement
                : block.getStatements()) {

            if (statement instanceof Assignment) {
                Assignment assignment =
                        (Assignment) statement;

                String variableName =
                        assignment.getTarget() != null
                                ? assignment.getTarget().toString()
                                : null;

                if (variableName != null) {
                    Object value = resolveContextValue(
                            assignment.getValue(),
                            availableValues
                    );

                    /*
                     * نحتفظ بالاسم حتى لو كانت القيمة
                     * ديناميكية وغير معروفة وقت الترجمة.
                     */
                    availableValues.put(
                            variableName,
                            value
                    );
                }

                continue;
            }

            if (statement instanceof ReturnStatement) {
                ReturnStatement returnStatement =
                        (ReturnStatement) statement;

                if (returnStatement.getValue()
                        instanceof FunctionCall) {

                    extractRenderTemplateContext(
                            (FunctionCall)
                                    returnStatement.getValue(),
                            availableValues
                    );
                }

                continue;
            }

            if (statement instanceof IfStatement) {
                IfStatement ifStatement =
                        (IfStatement) statement;

                collectTemplateContextsFromBlock(
                        ifStatement.getThenBlock(),
                        new LinkedHashMap<>(
                                availableValues
                        )
                );

                if (ifStatement.getElifParts() != null) {
                    for (ElifPart elifPart
                            : ifStatement.getElifParts()) {

                        collectTemplateContextsFromBlock(
                                elifPart.getBlock(),
                                new LinkedHashMap<>(
                                        availableValues
                                )
                        );
                    }
                }

                if (ifStatement.getElsePart() != null) {
                    collectTemplateContextsFromBlock(
                            ifStatement
                                    .getElsePart()
                                    .getBlock(),
                            new LinkedHashMap<>(
                                    availableValues
                            )
                    );
                }

                continue;
            }

            if (statement instanceof ForStatement) {
                ForStatement forStatement =
                        (ForStatement) statement;

                Map<String, Object> loopValues =
                        new LinkedHashMap<>(
                                availableValues
                        );

                loopValues.put(
                        forStatement.getIterator(),
                        null
                );

                collectTemplateContextsFromBlock(
                        forStatement.getBody(),
                        loopValues
                );

                continue;
            }

            if (statement instanceof WhileStatement) {
                WhileStatement whileStatement =
                        (WhileStatement) statement;

                collectTemplateContextsFromBlock(
                        whileStatement.getBody(),
                        new LinkedHashMap<>(
                                availableValues
                        )
                );
            }
        }
    }

    private void extractRenderTemplateContext(
            FunctionCall call,
            Map<String, Object> availableValues
    ) {
        if (!(call.getCallee()
                instanceof Identifier)) {
            return;
        }

        Identifier callee =
                (Identifier) call.getCallee();

        if (!"render_template".equals(
                callee.getName()
        )) {
            return;
        }

        List<Argument> arguments =
                call.getArgumentNodes();

        if (arguments.isEmpty()) {
            return;
        }

        Expression templateExpression =
                arguments.get(0).getValue();

        if (!(templateExpression
                instanceof StringLiteral)) {
            return;
        }

        String templateName =
                ((StringLiteral) templateExpression)
                        .getValue();

        Map<String, Object> templateContext =
                templateContexts.computeIfAbsent(
                        templateName,
                        ignored -> new LinkedHashMap<>()
                );

        for (int index = 1;
             index < arguments.size();
             index++) {

            Argument argument =
                    arguments.get(index);

            if (!argument.isKeyword()) {
                continue;
            }

            Object value = resolveContextValue(
                    argument.getValue(),
                    availableValues
            );

            templateContext.put(
                    argument.getName(),
                    value
            );
        }

        log(
                "Template context for "
                        + templateName
                        + ": "
                        + templateContext
        );
    }

    private Object resolveContextValue(
            Expression expression,
            Map<String, Object> availableValues
    ) {
        Object literalValue =
                extractValue(expression);

        if (literalValue != null) {
            return literalValue;
        }

        if (expression instanceof Identifier) {
            String variableName =
                    ((Identifier) expression)
                            .getName();

            return availableValues.get(
                    variableName
            );
        }
        return null;
    }


    private List<String> findTemplates(Program program) {
        List<String> templates = new ArrayList<>();
        findTemplatesInStatements(program.getStatements(), templates);
        if (templates.isEmpty()) {
            log("No templates referenced by render_template() were found in the Python AST.");
        }
        return templates;
    }

    private void findTemplatesInStatements(List<Statement> stmts, List<String> templates) {
        for (Statement stmt : stmts) {
            if (stmt instanceof FunctionDef) {
                FunctionDef func = (FunctionDef) stmt;
                if (func.body != null) findTemplatesInBlock(func.body, templates);
            }
        }
    }

    private void findTemplatesInBlock(Block block, List<String> templates) {
        for (Statement stmt : block.getStatements()) {
            if (stmt instanceof ReturnStatement) {
                ReturnStatement ret = (ReturnStatement) stmt;
                if (ret.getValue() instanceof FunctionCall) {
                    findTemplateInExpr(ret.getValue(), templates);
                }
            }
            if (stmt instanceof IfStatement) {
                IfStatement ifStmt = (IfStatement) stmt;
                if (ifStmt.getThenBlock() != null) findTemplatesInBlock(ifStmt.getThenBlock(), templates);
                if (ifStmt.getElsePart() != null && ifStmt.getElsePart().getBlock() != null)
                    findTemplatesInBlock(ifStmt.getElsePart().getBlock(), templates);
            }
        }
    }

    private void findTemplateInExpr(Expression expr, List<String> templates) {
        if (!(expr instanceof FunctionCall)) return;
        FunctionCall call = (FunctionCall) expr;
        boolean isRenderTemplate = false;
        if (call.getCallee() instanceof Identifier) {
            isRenderTemplate = "render_template".equals(((Identifier) call.getCallee()).getName());
        }
        if (isRenderTemplate && !call.getArguments().isEmpty()) {
            Expression first = call.getArguments().get(0);
            if (first instanceof StringLiteral) {
                String tmpl = ((StringLiteral) first).getValue();
                if (!templates.contains(tmpl)) {
                    templates.add(tmpl);
                    log("Found template: " + tmpl);
                }
            }
        }
    }


    private void generateHtml(String templateName) {
        String outputName = templateName.replace(".jinja", ".html");
        String outputPath = OUTPUT_DIR + outputName;
        log("Generating: " + templateName + " → " + outputName);

        String templatePath = "input" + File.separator + "templates" + File.separator + templateName;
        File templateFile = new File(templatePath);

        String html;
        if (templateFile.exists()) {
            try {
                String content = new String(Files.readAllBytes(templateFile.toPath()));
                html = resolveJinja(content, contextData);
                log("✓ Read from file: " + templatePath);
            } catch (IOException e) {
                log("✗ Could not read template: " + templatePath
                        + " — generation skipped. " + e.getMessage());
                return;
            }
        } else {
            log("✗ Template not found: " + templatePath
                    + " — generation skipped.");
            return;
        }

        try {
            Files.write(Paths.get(outputPath), html.getBytes("UTF-8"));
            log("✓ Written: " + outputPath);
        } catch (IOException e) {
            log("✗ Could not write: " + outputPath + " — " + e.getMessage());
        }
    }


    private String resolveJinja(String content, Map<String, Object> context) {
        StringBuilder result = new StringBuilder();
        String[] lines = content.split("\n");
        List<Map<String, Object>> currentLoop = null;
        String loopVar = null;
        List<String> loopBody = new ArrayList<>();
        boolean inLoop = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("{%") && trimmed.contains("for ") && trimmed.contains(" in ")) {
                String[] parts = trimmed.replace("{%", "").replace("%}", "").trim().split("\\s+");
                if (parts.length >= 4) {
                    loopVar = parts[1];
                    String listName = parts[3];
                    Object listObj = context.get(listName);
                    if (listObj instanceof List) {
                        currentLoop = new ArrayList<>();
                        for (Object item : (List<?>) listObj) {
                            if (item instanceof Map) currentLoop.add((Map<String, Object>) item);
                        }
                    }
                    inLoop = true;
                    loopBody.clear();
                }
                continue;
            }
            if (trimmed.startsWith("{%") && trimmed.contains("endfor")) {
                if (currentLoop != null && loopVar != null) {
                    for (Map<String, Object> item : currentLoop) {
                        for (String bodyLine : loopBody) {
                            result.append(resolveLoopLine(bodyLine, loopVar, item)).append("\n");
                        }
                    }
                }
                inLoop = false; currentLoop = null; loopVar = null; loopBody.clear();
                continue;
            }
            if (inLoop) { loopBody.add(line); continue; }
            if (trimmed.startsWith("{%")) continue;
            result.append(resolveVariables(line, context)).append("\n");
        }
        return result.toString();
    }

    private String resolveLoopLine(String line, String loopVar, Map<String, Object> item) {
        String result = line;
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            result = result.replace("{{ " + loopVar + "." + entry.getKey() + " }}", String.valueOf(entry.getValue()));
            result = result.replace("{{" + loopVar + "." + entry.getKey() + "}}", String.valueOf(entry.getValue()));
        }
        return result;
    }

    private String resolveVariables(String line, Map<String, Object> context) {
        String result = line;
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            result = result.replace("{{ " + entry.getKey() + " }}", String.valueOf(entry.getValue()));
            result = result.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
        }
        return result;
    }


    private void copyFile(String source, String dest) {
        try {
            Path sourcePath = Paths.get(source);
            if (Files.exists(sourcePath)) {
                Files.copy(sourcePath, Paths.get(dest),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                log("✓ Copied: " + source + " → " + dest);
            } else {
                log("✗ File not found for copy: " + source);
            }
        } catch (IOException e) {
            log("✗ Could not copy: " + source);
        }
    }

    private void copyDirectory(String source, String dest) {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(dest);

        if (!Files.isDirectory(sourcePath)) {
            throw new IllegalStateException(
                    "Required support directory not found: " + source
            );
        }

        try {
            if (Files.exists(destinationPath)) {
                try (java.util.stream.Stream<Path> existing =
                             Files.walk(destinationPath)) {
                    existing.sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.delete(path);
                                } catch (IOException exception) {
                                    throw new UncheckedIOException(exception);
                                }
                            });
                }
            }

            try (java.util.stream.Stream<Path> files = Files.walk(sourcePath)) {
                files.forEach(path -> {
                    Path relative = sourcePath.relativize(path);
                    Path target = destinationPath.resolve(relative);
                    try {
                        if (Files.isDirectory(path)) {
                            Files.createDirectories(target);
                        } else {
                            Files.createDirectories(target.getParent());
                            Files.copy(
                                    path,
                                    target,
                                    StandardCopyOption.REPLACE_EXISTING
                            );
                        }
                    } catch (IOException exception) {
                        throw new UncheckedIOException(exception);
                    }
                });
            }

            log("✓ Copied directory: " + source + " → " + dest);
        } catch (IOException | UncheckedIOException exception) {
            throw new IllegalStateException(
                    "Could not copy support directory: " + source,
                    exception
            );
        }
    }


    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            Files.createDirectories(Paths.get(COMPILER_OUT_DIR));
        } catch (IOException e) { System.err.println("Could not create output directories."); }
    }

    private void log(String msg) {
        generationLog.add(msg);
        System.out.println("[CodeGen] " + msg);
    }

    private void printSummary(List<String> templates) {
        System.out.println("\n=== Code Generation Summary ===");
        System.out.println("Context Variables: " + contextData.keySet());
        System.out.println("Generated Files:");
        System.out.println("  output/style.css");
        System.out.println("  output/script.js");
        System.out.println("  output/app.py");
        System.out.println("  output/pics/");
        System.out.println("Compiler reports are finalized by Main after all phases complete.");
        System.out.println("================================\n");
    }
    public Map<String, Object> getContextData() {
        return contextData;
    }

    public Map<String, Map<String, Object>>
    getTemplateContexts() {
        return templateContexts;
    }

    public List<String> getGenerationLog() {
        return Collections.unmodifiableList(generationLog);
    }
}
