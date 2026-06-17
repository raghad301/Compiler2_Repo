package AST.Web;


public class AttributeJinjaExpression extends AttributeValuePart {
    private JinjaExpression expression;

    public AttributeJinjaExpression(JinjaExpression expression,int line) {
        super(line);
        this.expression = expression;
    }
    @Override
    public String toString(){
        return "{{" + expression + "}}";
    }
}
