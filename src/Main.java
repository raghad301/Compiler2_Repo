import AST.Web.HtmlDocument;
import AST.parser_pkg.Program;
import Visitor.PythonVisitor;
import Visitor.WebVisitor;
import Web.WebLexer;
import Web.WebParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser_pkg.pythonLexer;
import parser_pkg.pythonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import Semantic.SemanticAnalyzer;
public class Main{
    public static void main(String[] args) {

        // Testing python(flask) AST & Symbol Table

//        String filePathP = "src/test.txt";
//
//        ParseTree tree = null;
//        try {
//            System.out.println("Reading from file: " + filePathP);
//            System.out.println("------------------------------------");
//
//            CharStream input = CharStreams.fromFileName(filePathP);
//            pythonLexer lexer = new pythonLexer(input);
//            CommonTokenStream tokens = new CommonTokenStream(lexer);
//
//            pythonParser parser = new pythonParser(tokens);
//            tree = parser.program();
//
//            PythonVisitor visitor = new PythonVisitor();
//            Program astRoot = (Program) visitor.visit(tree);
//
//            System.out.println("\n--- Final Abstract Syntax Tree ---");
//            if (astRoot != null) {
//                astRoot.print("");
//                SemanticAnalyzer analyzer = new SemanticAnalyzer();
//                analyzer.analyze(astRoot);
//
//                if (analyzer.hasErrors()) {
//                    System.err.println("Compilation stopped: semantic errors found.");
//                } else {
//                    System.out.println("Semantic analysis completed successfully.");
//                }
//            } else {
//                System.out.println("The AST is empty or null.");
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error: Could not read file. Make sure 'test.txt' exists.");
//        } catch (Exception e) {
//            System.err.println("Unexpected Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println("///////////// End Of Python Part /////////////");
//        System.out.println();
//        System.out.println();


// ================================================================
        // Testing Jinja2 AST & Symbol Table & Semantic Analysis
        // ================================================================
        WebVisitor visitor = null;
        try {
            String filePath = "src/web.txt";
            System.out.println("Reading from file: " + filePath);
            System.out.println("------------------------------------");

            String input = new String(Files.readAllBytes(Paths.get(filePath)));

            // 1. تنفيذ عملية الـ Lexing والـ Parsing وقراءة القواعد
            WebLexer lexer = new WebLexer(CharStreams.fromString(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WebParser parser = new WebParser(tokens);
            ParseTree jtree = parser.htmlDocument();

            // 2. بناء شجرة الـ AST للويب عبر الـ Visitor
            visitor = new WebVisitor();
            HtmlDocument ast = (HtmlDocument) visitor.visit(jtree);

            System.out.println("------ Abstract Syntax Tree (AST) From File ------");
            if (ast != null) {
                System.out.println(ast.toString());
                System.out.println("--------------------------------------------------");

                // 3. استدعاء المحلل الدلالي الخاص بالويب وفحص الأخطاء
                Semantic.WebSemanticAnalyzer webAnalyzer = new Semantic.WebSemanticAnalyzer();
                webAnalyzer.analyze(ast);

                // طباعة تقرير الأخطاء الدلالية للويب بالكونسول
                webAnalyzer.printResults();

                if (webAnalyzer.hasErrors()) {
                    System.err.println("Compilation stopped: Web semantic errors found. Process Aborted.");
                } else {
                    System.out.println("Web Semantic analysis completed successfully. Ready for next step!");

                    // ==========================================================
                    // 🔥 هنا بالظبط يتم ربط وتشغيل الـ Code Generation للويب 🔥
                    // ==========================================================
                    System.out.println("\n================================================");
                    System.out.println("------ Starting Code Generation Phase ------");
                    System.out.println("================================================");

                    // 1. إنشاء كائن المولد وتمرير جدول الرموز المستخرج من الـ Visitor
// تمرير كائن الـ Symbol Table مباشرة إلى الـ Generator
                    CodeGenerator.WebCodeGenerator generator = new CodeGenerator.WebCodeGenerator(visitor.getSymbolTable());
                    // 2. توليد كود الـ HTML الصافي والنقي من شجرة الـ AST
                    String finalHtml = generator.generate(ast);

                    // 3. طباعة الناتج النهائي المولد بالكونسول
                    System.out.println("\n------ Generated Pure HTML Output ------");
                    System.out.println(finalHtml);
                    System.out.println("--------------------------------------------------");
                }

            } else {
                System.out.println("The Web AST is empty or null.");
            }

        } catch (IOException e) {
            System.err.println("Error: Could not read file. Make sure 'web.txt' exists.");
        } catch (Exception e) {
            System.err.println("خطأ غير متوقع أثناء قراءة الملف أو التحليل: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. طباعة جدول الرموز للويب (Symbol Table)
        System.out.println("\n================================================");
        System.out.println("------ Symbol Table (Variables & Values) ------");
        System.out.println("================================================");
        if (visitor != null && visitor.getSymbolTable() != null) {
            visitor.getSymbolTable().print();
        } else {
            System.out.println("Visitor or Symbol Table was not initialized.");
        }
        System.out.println("--------------------------------------------------");
    }
}



