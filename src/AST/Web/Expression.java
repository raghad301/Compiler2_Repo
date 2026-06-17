package AST.Web;

public abstract class Expression extends HtmlNode {

    public Expression(int line){
        super(line);
    }
    @Override
    public abstract String toString();
}
