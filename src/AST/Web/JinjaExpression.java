package AST.Web;


public class JinjaExpression extends JinjaNode {
    private AST.Web.Expression expression;

    public JinjaExpression(Expression expression, int line ){
        super(line);
        this.expression = expression;
    }
    @Override
    public String toString(){
        return expression.toString();
    }

}
