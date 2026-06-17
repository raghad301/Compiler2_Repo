package AST.Web;

public abstract class JinjaNode extends HtmlNode {
    public int line;

    public JinjaNode(int line) {
        super(line);
    }

}
