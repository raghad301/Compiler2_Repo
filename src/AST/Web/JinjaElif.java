package AST.Web;

public class JinjaElif extends JinjaStatement {

    private AST.Web.Expression condition;

    public JinjaElif(Expression condition, int line){
        super(line);
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "elif "+condition;
    }
}
