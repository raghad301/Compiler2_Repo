package AST.Web;


public class CssJinjaStatement extends CssNode {

    private JinjaStatement statement;
    public CssJinjaStatement (JinjaStatement statement , int line){
        super(line);
        this.statement = statement;
    }

    @Override
    public String toString(){
        return "{%" +  statement + "%}";
    }
}
