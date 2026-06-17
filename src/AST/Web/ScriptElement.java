package AST.Web;

import java.util.ArrayList;

public class ScriptElement extends ScriptNode {
    private ArrayList<ScriptNode> children;

    public  ScriptElement(int line) {
        super(line);
        this.children = new ArrayList<>();

    }
    public void addChild(ScriptNode node){
        children.add(node);
    }
    public ArrayList<ScriptNode> getChildren() {
        return children;
    }
    @Override
    public String toString(){
        return "<script>" +  children + "</script>";
    }


}
