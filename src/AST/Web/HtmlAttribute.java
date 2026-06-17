package AST.Web;

import java.util.ArrayList;

public class HtmlAttribute extends HtmlNode {
    private  String name;
    private ArrayList<AttributeValuePart> parts;
    public HtmlAttribute(String name, int line){
        super(line);
        this.name = name;
        this.parts = new ArrayList<>();
    }
    public void addPart(AttributeValuePart part){
        parts.add(part);
    }
    @Override
    public String toString(){
          return "Attribute(" + name + ")";
    }
}
