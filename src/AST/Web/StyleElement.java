package AST.Web;

import java.util.ArrayList;

public class StyleElement extends CssNode {

    private ArrayList<CssNode> children = new ArrayList<>();

    public StyleElement(int line) {
        super(line);
    }

    public void addChild(CssNode node) {
        children.add(node);
    }

    public ArrayList<CssNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StyleElement (Line: ").append(line).append(")");
        for (CssNode child : children) {
            sb.append("\n  |-- ").append(child.toString().replace("\n", "\n  "));
        }
        return sb.toString();
    }
}