package AST.Web;

public class AttributeText extends AttributeValuePart {

    private final String text;

    public AttributeText(int line, String text) {
        super(line);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
