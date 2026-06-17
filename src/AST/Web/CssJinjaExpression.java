package AST.Web;


public class CssJinjaExpression extends CssNode {
    private JinjaExpression expression;

    public CssJinjaExpression(JinjaExpression expression,int line){
        super(line);
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "{{" +  expression + "}}";
    }
}
