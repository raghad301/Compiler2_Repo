package AST.Web;

public class ScriptText extends ScriptNode{

    private String text;
    public ScriptText(String text , int line){
        super(line);
        this.text = text;
    }
    public String getText(){return this.text;}

    @Override
    public String toString(){
        return text;
    }
}
