package AST.Web;

public class CssText extends CssNode{

    private String text;

    public CssText(String text, int line){
        super(line);
        this.text = text;
    }
    @Override
    public String toString(){
        return text;
    }
}
