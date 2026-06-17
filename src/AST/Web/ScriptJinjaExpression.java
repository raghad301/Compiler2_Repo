package AST.Web;

public class ScriptJinjaExpression extends ScriptNode
{
    private JinjaExpression expression;

    public  ScriptJinjaExpression(JinjaExpression expression,int line) {
        super(line);
        this.expression = expression;
    }

    public JinjaExpression getExpression() {
        return expression;
    }
    @Override
    public String  toString()
    {
        return "{{" + expression + "}}";
    }
}
