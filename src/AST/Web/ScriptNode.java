package AST.Web;

public abstract class ScriptNode {
    public int line;

    public ScriptNode(int line){
        this.line = line;
    }
    public int getLine(){return line;}

    @Override
    public abstract String toString();

}
