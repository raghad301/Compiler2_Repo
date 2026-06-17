package AST.Web;

public class JinjaElse extends JinjaStatement {

    public  JinjaElse(int line){
        super(line);
    }
    @Override
    public String toString(){
        return "else ";
    }
}
