package AST.Web;

import java.util.ArrayList;

public class StyleElement extends CssNode{

    private ArrayList<CssNode> children=new ArrayList<>();

    public StyleElement(int line) {
        super(line);
    }
    public void addChild(CssNode node){
        children.add(node);
    }
    @Override
    public String toString(){
        return "<style>" + children + "</style>";
    }
}
