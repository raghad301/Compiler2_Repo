package AST.Web;

public class JinjaIf extends JinjaStatement {
    private AST.Web.Expression condition;

    public JinjaIf(Expression condition, int line) {
        super(line);
        this.condition = condition;
    }
    @Override
    public String toString(){
        return "if "+condition ;
    }
}
