package AST.Web;

public abstract class HtmlNode {
    protected int line;
    protected int column;

    public HtmlNode(int line) {
        this.line = line;
    }
    public int getLine() {return line;}

    @Override
    public abstract String toString() ;
}
