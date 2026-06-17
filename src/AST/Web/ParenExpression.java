package AST.Web;

public class ParenExpression extends AST.Web.Expression {
    private AST.Web.Expression expression;

    public ParenExpression(Expression expression, int line){
        super(line);
        this.expression = expression;
    }
    @Override
    public String toString(){
        return "("+ expression + ")";
    }
}
