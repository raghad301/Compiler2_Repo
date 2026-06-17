package AST.Web;

public abstract class CssNode {
    public int line;
    public int column;

    public CssNode(int line) {
        this.line = line;
    }
    @Override
    public abstract String toString() ;
}
