package AST.Web;

public class AttributeJinjaExpression extends AttributeValuePart {

    private final HtmlExpressionBlock expression;

    public AttributeJinjaExpression(
            HtmlExpressionBlock expression,
            int line
    ) {
        super(line);
        this.expression = expression;
    }

    public HtmlExpressionBlock getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        if (expression == null) {
            return "";
        }

        return "{{ " + expression.getExpression() + " }}";
    }
}
