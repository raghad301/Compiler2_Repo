package AST.Web;

public class HtmlExpressionBlock extends HtmlNode {
    private String expression;

    public HtmlExpressionBlock(String expression, int line) {
        super(line);
        this.expression = expression;
    }

    public String getExpression() { return expression; }

    @Override
    public String toString() {
        return "JinjaExpr ({{ " + expression + " }}) (Line: " + line + ")";
    }
}