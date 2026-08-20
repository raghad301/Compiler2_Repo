package AST.Web;

public class HtmlText extends HtmlNode {
    private String text;

    public HtmlText(String text, int line) {
        super(line);
        this.text = text;
    }

    public String getText() { return text; } // مضافة

    @Override
    public String toString() {
        return "HtmlTextNode (\"" + text.trim() + "\") (Line: " + line + ")";
    }
}