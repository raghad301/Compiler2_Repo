package AST.Web;

public class JinjaEndFor extends JinjaStatement {

    public JinjaEndFor(int line ){
        super(line);
    }
    @Override
    public String toString(){
        return "endfor ";
    }
}
