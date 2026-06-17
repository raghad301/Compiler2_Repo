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

public class Main{
    public static void main(String[] args) {

        // Testing python(flask) AST & Symbol Table

        String filePathP = "src/parser_pkg/test.txt";

        ParseTree tree = null;
        try {
            System.out.println("Reading from file: " + filePathP);
            System.out.println("------------------------------------");

            CharStream input = CharStreams.fromFileName(filePathP);
            pythonLexer lexer = new pythonLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            pythonParser parser = new pythonParser(tokens);
            tree = parser.program();

            PythonVisitor visitor = new PythonVisitor();
            Program astRoot = (Program) visitor.visit(tree);

            System.out.println("\n--- Final Abstract Syntax Tree ---");
            if (astRoot != null) {
                astRoot.print("");
            } else {
                System.out.println("The AST is empty or null.");
            }

        } catch (IOException e) {
            System.err.println("Error: Could not read file. Make sure 'test.txt' exists.");
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println("///////////// End Of Python Part /////////////");
        System.out.println();
        System.out.println();


        
        // Testing Jinja2 AST & Symbol Table

        WebVisitor visitor = null;
        try {

            String filePath = "web.txt";
            String input = new String(Files.readAllBytes(Paths.get(filePath)));

            // تنفيذ عملية الـ Parsing كالمعتاد
            WebLexer lexer = new WebLexer(CharStreams.fromString(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WebParser parser = new WebParser(tokens);
            ParseTree jtree = parser.htmlDocument();

            visitor = new WebVisitor();
            HtmlDocument ast = (HtmlDocument) visitor.visit(jtree);

            System.out.println("------ Abstract Syntax Tree (AST) From File ------");
            System.out.println(ast.toString());
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.err.println("خطأ أثناء قراءة الملف أو التحليل: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("================================================");
        System.out.println("------ Symbol Table (Variables & Values) ------");
        System.out.println("================================================");
        if (visitor != null) {
            visitor.getSymbolTable().print();
        } else {
            System.out.println("Visitor was not initialized.");
        }
        System.out.println("--------------------------------------------------");

    }
}



