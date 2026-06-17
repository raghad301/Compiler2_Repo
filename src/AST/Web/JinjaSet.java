package AST.Web;

public class JinjaSet extends JinjaStatement {
    private String name;
    private Expression expression;

    public JinjaSet(String name, Expression expression ,int line){
        super(line);
        this.name = name;
        this.expression = expression;
    }
    @Override
    public String toString() {

        return "JinjaSet (Variable: " + name + ") (Line: " + getLine() + ")";
    }
}
