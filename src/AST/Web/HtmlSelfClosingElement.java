package AST.Web;

import java.util.ArrayList;

public class HtmlSelfClosingElement extends HtmlNode {

    private final String tagName;
    private final ArrayList<HtmlAttribute> attributes;

    public HtmlSelfClosingElement(String tagName, int line) {
        super(line);
        this.tagName = tagName;
        this.attributes = new ArrayList<>();
    }

    public void addAttribute(HtmlAttribute attribute) {
        if (attribute != null) {
            attributes.add(attribute);
        }
    }

    public String getTagName() {
        return tagName;
    }

    public ArrayList<HtmlAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("HtmlSelfClosingElement [<")
                .append(tagName)
                .append(">] (Line: ")
                .append(line)
                .append(")");

        if (!attributes.isEmpty()) {
            result.append(" Attributes: ")
                    .append(attributes);
        }

        return result.toString();
    }
}