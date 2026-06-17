package AST.Web;

public class StringLiteral extends AST.Web.Expression {

    private String value;
    public StringLiteral(String value , int line){
        super(line);
        this.value = value;
    }
    @Override
    public String toString(){
        return value;
    }
}
