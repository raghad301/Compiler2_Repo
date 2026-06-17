package AST.Web;

public class MulDiv extends AST.Web.Expression {

    private AST.Web.Expression left;
    private String operator;
    private AST.Web.Expression right;

    public MulDiv(Expression left, String operator, Expression right, int line){
        super(line);
        this.left=left;
        this.operator=operator;
        this.right=right;
    }
    @Override
    public String toString(){
        return "("+left+" "+operator+" "+right+")";
    }
}
