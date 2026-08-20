package AST.Web;

import java.util.ArrayList;

public class HtmlElement extends HtmlNode {
    private String tagName;
    private ArrayList<HtmlAttribute> attributes;
    private ArrayList<HtmlNode> children;

    public HtmlElement(String tagName, int line) {
        super(line);
        this.tagName = tagName;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void addAttribute(HtmlAttribute attribute) {
        attributes.add(attribute);
    }

    public void addChild(HtmlNode child) {
        children.add(child);
    }

    public String getTagName() { return tagName; }
    public ArrayList<HtmlNode> getChildren() { return children; }
    public ArrayList<HtmlAttribute> getAttributes() { return attributes; } // مضافة للـ JSON

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HtmlElement [").append(tagName).append("] (Line: ").append(line).append(")");
        if (!attributes.isEmpty()) {
            sb.append(" Attributes: ").append(attributes);
        }
        if (!children.isEmpty()) {
            for (HtmlNode child : children) {
                sb.append("\n  |-- ").append(child.toString().replace("\n", "\n  "));
            }
        }
        return sb.toString();
    }
}