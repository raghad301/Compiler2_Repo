package AST.Web;


public class Comparison extends AST.Web.Expression {

    private AST.Web.Expression left;
    private String operator;
    private AST.Web.Expression right;
    public Comparison(AST.Web.Expression left, String operator, AST.Web.Expression right , int line)
    {
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
