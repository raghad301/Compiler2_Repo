package AST.Web;

import java.util.ArrayList;

public class HtmlDocument extends HtmlNode {

    private ArrayList<HtmlNode> children;

    public HtmlDocument(int line) {
        super(line);
        this.children = new ArrayList<>();
    }
    public void addChild(HtmlNode node) {
        children.add(node);
    }

    public ArrayList<HtmlNode> getChildren() { return children; } // مضافة للـ JSON

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HtmlDocument (Line: ").append(line).append(")\n");
        for (HtmlNode child : children) {
            sb.append("  |-- ").append(child.toString().replace("\n", "\n  ")).append("\n");
        }
        return sb.toString();
    }
}