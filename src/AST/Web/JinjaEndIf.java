package AST.Web;

public class JinjaEndIf extends JinjaStatement {

    public JinjaEndIf(int line){
        super(line);
    }
    @Override
    public String toString(){
        return "endif ";
    }
}
