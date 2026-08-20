package AST.Web;

import java.util.ArrayList;

public class HtmlAttribute extends HtmlNode {

    private final String name;
    private final ArrayList<AttributeValuePart> parts;

    public HtmlAttribute(String name, int line) {
        super(line);
        this.name = name;
        this.parts = new ArrayList<>();
    }

    public void addPart(AttributeValuePart part) {
        if (part != null) {
            parts.add(part);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<AttributeValuePart> getParts() {
        return parts;
    }

    public boolean hasValue() {
        return !parts.isEmpty();
    }

    public String getValue() {
        StringBuilder value = new StringBuilder();

        for (AttributeValuePart part : parts) {
            value.append(part.toString());
        }

        return value.toString();
    }

    @Override
    public String toString() {
        if (!hasValue()) {
            return "Attribute(" + name + ")";
        }

        return "Attribute("
                + name
                + "=\""
                + getValue()
                + "\")";
    }
}