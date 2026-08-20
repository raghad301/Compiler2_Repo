package AST.Web;

public class HtmlSetStatement extends HtmlNode {
    private String varName;
    private String expression;

    public HtmlSetStatement(String varName, String expression, int line) {
        super(line);
        this.varName = varName;
        this.expression = expression;
    }

    public String getVarName() { return varName; }
    public String getExpression() { return expression; }

    @Override
    public String toString() {
        return "JinjaSet [" + varName + " = " + expression + "] (Line: " + line + ")";
    }
}