package AST.Web;

public class NumberLiteral extends Expression {

    private String value;
    public NumberLiteral(String value , int line){
        super(line);
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
