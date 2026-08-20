import AST.Web.HtmlDocument;
import AST.parser_pkg.Program;
import Semantic.WebSemanticAnalyzer;
import Visitor.PythonVisitor;
import Visitor.WebVisitor;
import Web.WebLexer;
import Web.WebParser;
import Semantic.SemanticAnalyzer;
import Semantic.SemanticError;
import CodeGenerator.PythonCodeGenerator;
import CodeGenerator.WebCodeGenerator;
import CodeGenerator.AstPythonWriter;
import CodeGenerator.AstJinjaWriter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import parser_pkg.pythonLexer;
import parser_pkg.pythonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.nio.charset.StandardCharsets;

public class Main {

    private static boolean globalSemanticError = false;

    public static void main(String[] args) {
        globalSemanticError = false;

        File outputDir = new File("output");
        File compilerOutputDir = new File("compiler_output");
        if (!outputDir.exists()) outputDir.mkdirs();
        if (!compilerOutputDir.exists()) compilerOutputDir.mkdirs();

        StringBuilder semanticReportLog =
                new StringBuilder("=== Syntax and Semantic Report ===\n");
        StringBuilder generationLog =
                new StringBuilder("=== Generation Log ===\n");
        Map<String, HtmlDocument> webAsts = new LinkedHashMap<>();

        // ================================================================
        // 1. Python Part — Syntax Parsing + Semantic Analysis
        // ================================================================
        String filePathP = "input/app.py";
        Program astRoot = null;
        PythonCodeGenerator pyCodeGen = null;

        try {
            System.out.println("Reading from file: " + filePathP);
            System.out.println("------------------------------------");

            CharStream input = CharStreams.fromFileName(filePathP);
            pythonLexer lexer = new pythonLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            pythonParser parser = new pythonParser(tokens);

            VerboseErrorListener pyLexerErrors = new VerboseErrorListener(filePathP, "python-lexer");
            VerboseErrorListener pyParserErrors = new VerboseErrorListener(filePathP, "python-parser");
            lexer.removeErrorListeners();
            lexer.addErrorListener(pyLexerErrors);
            parser.removeErrorListeners();
            parser.addErrorListener(pyParserErrors);

            ParseTree tree = parser.program();

            if (pyLexerErrors.hasErrors() || pyParserErrors.hasErrors()) {
                globalSemanticError = true;
                appendSyntaxErrors(semanticReportLog, pyLexerErrors, pyParserErrors);
                System.err.println("❌ Python syntax analysis failed — AST, semantic analysis, context extraction and generation skipped.");
            } else {
                PythonVisitor visitor = new PythonVisitor();
                astRoot = (Program) visitor.visit(tree);
                if (astRoot != null) {
                    System.out.println("\n--- Complete Python AST ---");
                    astRoot.print("");
                    System.out.println("--- End Complete Python AST ---\n");
                }
            }

            if (astRoot != null) {
                System.out.println("\n--- Python Abstract Syntax Tree Generated ---");

                SemanticAnalyzer analyzer = new SemanticAnalyzer();
                analyzer.analyze(astRoot);

                if (analyzer.hasErrors()) {
                    System.err.println("❌ Python Semantic Analysis Failed!");
                    semanticReportLog.append("=== Python Semantic Errors ===\n");
                    for (SemanticError err : analyzer.getErrors()) {
                        semanticReportLog.append("SemanticError{file='")
                                .append(filePathP).append("', line=")
                                .append(err.getLine())
                                .append(", phase='python-semantic', message='")
                                .append(err.getMessage()).append("'}\n");
                    }
                    globalSemanticError = true;
                    astRoot = null;
                } else {
                    System.out.println("✓ Python Semantic analysis completed successfully.");
                    semanticReportLog.append("✓ Python Semantic analysis passed cleanly.\n");
                }
            }

        } catch (IOException e) {
            System.err.println("Error: Could not read file " + filePathP);
        } catch (Exception e) {
            System.err.println("Unexpected Error in Python phase: " + e.getMessage());
            e.printStackTrace();
        }

        // Python Code Generation
        if (astRoot != null) {
            System.out.println("\n================================================");
            System.out.println("------ Starting Python Code Generation ------");
            System.out.println("================================================");

            try {
                pyCodeGen = new PythonCodeGenerator();
                pyCodeGen.generate(astRoot);
                for (String entry : pyCodeGen.getGenerationLog()) {
                    generationLog.append(entry).append("\n");
                }
                new AstPythonWriter().write(
                        astRoot,
                        pyCodeGen.getContextData(),
                        pyCodeGen.getTemplateContexts(),
                        Paths.get("compiler_output", "ast_python.json")
                );
                generationLog.append("✓ Written compiler_output/ast_python.json\n");
            } catch (Exception exception) {
                generationLog.append("❌ Python Code Generation failed: ")
                        .append(exception.getMessage()).append("\n");
                System.err.println("❌ Python Code Generation failed: "
                        + exception.getMessage());
                globalSemanticError = true;
                astRoot = null;
                pyCodeGen = null;
            }
        }

        System.out.println("\n///////////// End Of Python Part /////////////\n");

        // ================================================================
        // 2. Web/Jinja Part — Analysis and Generation
        // ================================================================
        Map<String, Map<String, Object>>
                templateContexts =
                (pyCodeGen != null)
                        ? pyCodeGen.getTemplateContexts()
                        : Collections.emptyMap();

        if (!templateContexts.isEmpty()) {
            System.out.println(
                    "[Integration] Template contexts ready: "
                            + templateContexts.keySet()
            );
        }

        File templatesFolder = new File("input", "templates");
        File[] jinjaFiles = templatesFolder.listFiles((dir, name) -> name.endsWith(".jinja") || name.endsWith(".html"));
        if (jinjaFiles != null) {
            Arrays.sort(jinjaFiles, (left, right) ->
                    left.getName().compareToIgnoreCase(right.getName()));
        }
        removeOrphanedHtmlOutputs(jinjaFiles);

        if (astRoot == null || pyCodeGen == null) {
            globalSemanticError = true;
            semanticReportLog.append("Web/Jinja analysis and generation skipped because the Python phase failed.\n");
            invalidateGeneratedOutputs(jinjaFiles);
            deleteAstReports(true, generationLog);
            System.err.println("❌ Web/Jinja phase skipped because Python did not produce a valid analyzed AST.");
        } else if (jinjaFiles != null && jinjaFiles.length > 0) {
            for (File jinjaFile : jinjaFiles) {
                Map<String, Object> templateContext =
                        templateContexts.getOrDefault(
                                jinjaFile.getName(),
                                Collections.emptyMap()
                        );

                System.out.println(
                        "[Integration] Context for "
                                + jinjaFile.getName()
                                + ": "
                                + templateContext.keySet()
                );

                boolean hasErrorInFile =
                        analyzeAndGenerateJinja(
                                jinjaFile,
                                templateContext,
                                semanticReportLog,
                                generationLog,
                                webAsts
                        );
                if (hasErrorInFile) {
                    globalSemanticError = true;
                }
            }
            try {
                new AstJinjaWriter().writeAll(
                        webAsts,
                        Paths.get("compiler_output"));
                generationLog.append("✓ Written compiler_output/ast_jinja.json and ")
                        .append(webAsts.size()).append(" per-template AST report(s).\n");
            } catch (IOException exception) {
                globalSemanticError = true;
                generationLog.append("❌ Could not write Web/Jinja AST reports: ")
                        .append(exception.getMessage()).append("\n");
                System.err.println("❌ Could not write Web/Jinja AST reports: "
                        + exception.getMessage());
            }
        } else {
            globalSemanticError = true;
            semanticReportLog.append(
                    "No template files found in input/templates/ directory.\n");
            invalidateGeneratedOutputs(jinjaFiles);
            deleteAstReports(false, generationLog);
            System.err.println("Error: No template files found in input/templates/ directory!");
        }

        // ================================================================
        // 3. Final Outputs & Reports
        // ================================================================
        try {
            String status = globalSemanticError ? "FAILURE" : "SUCCESS";
            semanticReportLog.append("\nCompilation Status: ").append(status).append("\n");
            generationLog.append("Compilation Status: ").append(status).append("\n");
            writeToFile("compiler_output/semantic_report.txt", semanticReportLog.toString());
            writeToFile("compiler_output/generation_log.txt", generationLog.toString());

            System.out.println("\n================================================");
            System.out.println("--- All Reports & Outputs Processed ---");
            System.out.println("================================================");

            if (!globalSemanticError) {
                System.out.println("🎉 === All Compilation Phases Completed Successfully! ===");
            } else {
                System.err.println(
                        "⚠️ Compilation failed — check semantic_report.txt and generation_log.txt "
                                + "and generation is prevented for faulty files."
                );
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("Error finalizing outputs: " + e.getMessage());
            System.exit(1);
        }
    }

    private static boolean analyzeAndGenerateJinja(
            File file,
            Map<String, Object> pythonContext,
            StringBuilder semanticLog,
            StringBuilder genLog
    ) {
        return analyzeAndGenerateJinja(
                file,
                pythonContext,
                semanticLog,
                genLog,
                new LinkedHashMap<>());
    }

    private static boolean analyzeAndGenerateJinja(
            File file,
            Map<String, Object> pythonContext,
            StringBuilder semanticLog,
            StringBuilder genLog,
            Map<String, HtmlDocument> webAsts
    ) {
        try {
            System.out.println("\nProcessing Jinja File: " + file.getPath());
            System.out.println("------------------------------------");

            CharStream input = CharStreams.fromFileName(file.getPath());
            WebLexer lexer = new WebLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WebParser parser = new WebParser(tokens);

            VerboseErrorListener lexerErrors = new VerboseErrorListener(file.getPath(), "web-lexer");
            VerboseErrorListener parserErrors = new VerboseErrorListener(file.getPath(), "web-parser");
            lexer.removeErrorListeners();
            lexer.addErrorListener(lexerErrors);
            parser.removeErrorListeners();
            parser.addErrorListener(parserErrors);

            ParseTree jtree = parser.htmlDocument();
            validateWebStructure(tokens, parserErrors);
            if (lexerErrors.hasErrors() || parserErrors.hasErrors()) {
                appendSyntaxErrors(semanticLog, lexerErrors, parserErrors);
                deleteStaleTemplateOutput(file);
                System.err.println("❌ Web/Jinja syntax analysis failed for " + file.getName()
                        + " — AST, semantic analysis and generation skipped.");
                return true;
            }
            WebVisitor webVisitor = new WebVisitor();

            if (pythonContext != null && webVisitor.getSymbolTable() != null) {
                pythonContext.keySet().forEach(key ->
                        webVisitor.getSymbolTable().define(key, "PythonContext", 0));
            }

            HtmlDocument ast = (HtmlDocument) webVisitor.visit(jtree);


            if (ast != null) {
                webAsts.put(file.getName(), ast);
                System.out.println(
                        "\n--- Web/Jinja AST: "
                                + file.getName()
                                + " ---"
                );

                System.out.println(ast);

                System.out.println(
                        "--- End Web/Jinja AST: "
                                + file.getName()
                        + " ---\n"
                );

                System.out.println("--- Web Symbol Table: " + file.getName() + " ---");
                webVisitor.getSymbolTable().print();
                System.out.println("--- End Web Symbol Table: " + file.getName() + " ---\n");
            }

            if (ast != null) {
                WebSemanticAnalyzer webAnalyzer = new WebSemanticAnalyzer();
                if (pythonContext != null) {
                    webAnalyzer.setPythonContext(pythonContext);
                }
                webAnalyzer.analyze(ast);

                // منع التوليد تماماً عند وجود أخطاء دلالية
                if (webAnalyzer.hasErrors()) {
                    System.err.println("❌ Web Semantic Analysis Failed for " + file.getName() + " — Generation Prevented!");
                    semanticLog.append("❌ Web Semantic Errors in ").append(file.getName()).append(" (Generation Prevented)\n");
                    for (SemanticError err : webAnalyzer.getErrors()) {
                        semanticLog.append("SemanticError{file='")
                                .append(file.getPath()).append("', line=")
                                .append(err.getLine())
                                .append(", phase='web-semantic', message='")
                                .append(err.getMessage()).append("'}\n");
                    }
                    deleteStaleTemplateOutput(file);
                    return true; // يوجد خطأ
                } else {
                    System.out.println("✓ Web Semantic analysis completed successfully for " + file.getName());
                    semanticLog.append("✓ Web Semantic analysis passed for ").append(file.getName()).append("\n");
                    String html = new WebCodeGenerator(webVisitor.getSymbolTable(), pythonContext).generate(ast);
                    String outputName = file.getName().replaceFirst("\\.(jinja|html)$", ".html");
                    Files.write(Paths.get("output", outputName), html.getBytes(StandardCharsets.UTF_8));
                    genLog.append("✓ Generated output/").append(outputName).append(" from ")
                            .append(file.getName()).append("\n");
                    return false; // لا يوجد خطأ
                }
            }
        } catch (WebCodeGenerator.GenerationException e) {
            String message = "Code generation failed for " + file.getName()
                    + ": " + e.getMessage();
            System.err.println("❌ " + message);
            genLog.append("❌ ").append(message).append("\n");
        } catch (Exception e) {
            System.err.println("Error processing " + file.getName() + ": " + e.getMessage());
            genLog.append("❌ Error processing ").append(file.getName())
                    .append(": ").append(e.getMessage()).append("\n");
        }
        deleteStaleTemplateOutput(file);
        return true;
    }

    private static void appendSyntaxErrors(StringBuilder log, VerboseErrorListener... listeners) {
        for (VerboseErrorListener listener : listeners) {
            for (VerboseErrorListener.SyntaxError error : listener.getErrors()) {
                log.append("SyntaxError{file='").append(error.file)
                        .append("', line=").append(error.line)
                        .append(", column=").append(error.column)
                        .append(", phase='").append(error.phase)
                        .append("', message='").append(error.message).append("'}\n");
            }
        }
    }

    private static void validateWebStructure(CommonTokenStream tokens, VerboseErrorListener errors) {
        tokens.fill();
        List<Token> all = tokens.getTokens();
        Deque<Token> htmlTags = new ArrayDeque<>();
        Deque<Token> jinjaBlocks = new ArrayDeque<>();

        for (int i = 0; i < all.size(); i++) {
            Token token = all.get(i);
            int type = token.getType();

            if (type == WebLexer.IFKW || type == WebLexer.FORKW) {
                jinjaBlocks.push(token);
            } else if (type == WebLexer.ELIFKW || type == WebLexer.ELSEKW) {
                if (jinjaBlocks.isEmpty() || jinjaBlocks.peek().getType() != WebLexer.IFKW) {
                    errors.report(token.getLine(), token.getCharPositionInLine(),
                            "'" + token.getText() + "' without a matching if block");
                }
            } else if (type == WebLexer.ENDIFKW || type == WebLexer.ENDFORKW) {
                int expected = type == WebLexer.ENDIFKW ? WebLexer.IFKW : WebLexer.FORKW;
                if (jinjaBlocks.isEmpty() || jinjaBlocks.peek().getType() != expected) {
                    errors.report(token.getLine(), token.getCharPositionInLine(),
                            "unmatched Jinja closing statement '" + token.getText() + "'");
                } else {
                    jinjaBlocks.pop();
                }
            }

            if (type == WebLexer.TAG_OPEN && i + 1 < all.size()) {
                Token next = all.get(i + 1);
                if (next.getType() == WebLexer.TAG_SLASH && i + 2 < all.size()) {
                    Token closing = all.get(i + 2);
                    if (closing.getType() == WebLexer.TAG_NAME) {
                        if (htmlTags.isEmpty()) {
                            errors.report(closing.getLine(), closing.getCharPositionInLine(),
                                    "closing tag </" + closing.getText() + "> has no opening tag");
                        } else {
                            Token opening = htmlTags.pop();
                            if (!opening.getText().equalsIgnoreCase(closing.getText())) {
                                errors.report(closing.getLine(), closing.getCharPositionInLine(),
                                        "closing tag </" + closing.getText() + "> does not match <"
                                                + opening.getText() + ">");
                            }
                        }
                    }
                } else if (next.getType() == WebLexer.TAG_NAME) {
                    boolean explicitlySelfClosed = false;
                    for (int j = i + 2; j < all.size(); j++) {
                        int laterType = all.get(j).getType();
                        if (laterType == WebLexer.TAG_SLASH_CLOSE) {
                            explicitlySelfClosed = true;
                            break;
                        }
                        if (laterType == WebLexer.TAG_CLOSE) break;
                    }
                    if (!explicitlySelfClosed) htmlTags.push(next);
                }
            }
        }

        while (!jinjaBlocks.isEmpty()) {
            Token opening = jinjaBlocks.removeLast();
            errors.report(opening.getLine(), opening.getCharPositionInLine(),
                    "unclosed Jinja '" + opening.getText() + "' block");
        }
        while (!htmlTags.isEmpty()) {
            Token opening = htmlTags.removeLast();
            errors.report(opening.getLine(), opening.getCharPositionInLine(),
                    "unclosed HTML tag <" + opening.getText() + ">");
        }
    }

    private static void deleteStaleTemplateOutput(File template) {
        String outputName = template.getName().replaceFirst("\\.(jinja|html)$", ".html");
        try {
            if (Files.deleteIfExists(Paths.get("output", outputName))) {
                System.out.println("Removed stale output/output file: output/" + outputName);
            }
        } catch (IOException e) {
            System.err.println("Could not remove stale output/" + outputName + ": " + e.getMessage());
        }
    }

    private static void invalidateGeneratedOutputs(File[] templates) {
        String[] supportOutputs = {"app.py", "style.css", "script.js"};
        for (String name : supportOutputs) {
            try {
                Files.deleteIfExists(Paths.get("output", name));
            } catch (IOException e) {
                System.err.println("Could not remove stale output/" + name + ": " + e.getMessage());
            }
        }
        deleteGeneratedDirectory(Paths.get("output", "pics"));
        File[] generatedHtmlFiles = new File("output")
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".html"));
        if (generatedHtmlFiles != null) {
            for (File generatedHtml : generatedHtmlFiles) {
                try {
                    if (Files.deleteIfExists(generatedHtml.toPath())) {
                        System.out.println("Removed stale output file: " + generatedHtml.getPath());
                    }
                } catch (IOException e) {
                    System.err.println("Could not remove stale " + generatedHtml.getPath()
                            + ": " + e.getMessage());
                }
            }
        }
    }

    private static void deleteGeneratedDirectory(Path directory) {
        if (!Files.exists(directory)) {
            return;
        }

        try (java.util.stream.Stream<Path> paths = Files.walk(directory)) {
            paths.sorted(java.util.Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException exception) {
                            throw new java.io.UncheckedIOException(exception);
                        }
                    });
        } catch (IOException | java.io.UncheckedIOException exception) {
            System.err.println("Could not remove stale " + directory + ": "
                    + exception.getMessage());
        }
    }

    private static void removeOrphanedHtmlOutputs(File[] templates) {
        Set<String> expectedOutputs = new HashSet<>();
        if (templates != null) {
            for (File template : templates) {
                expectedOutputs.add(template.getName()
                        .replaceFirst("\\.(jinja|html)$", ".html")
                        .toLowerCase());
            }
        }

        File[] generatedHtmlFiles = new File("output")
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".html"));
        if (generatedHtmlFiles == null) {
            return;
        }

        for (File generatedHtml : generatedHtmlFiles) {
            if (expectedOutputs.contains(generatedHtml.getName().toLowerCase())) {
                continue;
            }
            try {
                if (Files.deleteIfExists(generatedHtml.toPath())) {
                    System.out.println("Removed orphaned output file: " + generatedHtml.getPath());
                }
            } catch (IOException e) {
                System.err.println("Could not remove orphaned " + generatedHtml.getPath()
                        + ": " + e.getMessage());
            }
        }
    }

    private static void writeToFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));
    }

    private static void deleteAstReports(
            boolean includePython,
            StringBuilder generationLog
    ) {
        try {
            new AstJinjaWriter().deleteReports(Paths.get("compiler_output"));
            if (includePython) {
                Files.deleteIfExists(Paths.get("compiler_output", "ast_python.json"));
            }
            generationLog.append("Removed stale AST reports after a failed phase.\n");
        } catch (IOException exception) {
            generationLog.append("Could not remove stale AST reports: ")
                    .append(exception.getMessage()).append("\n");
        }
    }

    private static void copyFileIfExists(String sourcePath, String destPath) {
        File src = new File(sourcePath);
        if (src.exists()) {
            try {
                Files.copy(Paths.get(sourcePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied " + sourcePath + " -> " + destPath);
            } catch (IOException e) {
                System.err.println("Failed to copy " + sourcePath + ": " + e.getMessage());
            }
        }
    }
}
