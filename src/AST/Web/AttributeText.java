package AST.Web;

public class AttributeText extends AttributeValuePart {
    private String text;

    public AttributeText(int line, String text) {
        super(line);
        this.text = text;

    }
    @Override
    public String toString(){
        return text;
    }
}
