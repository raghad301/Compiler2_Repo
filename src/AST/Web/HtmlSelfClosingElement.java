package AST.Web;

import java.util.ArrayList;

public class HtmlSelfClosingElement extends HtmlNode {

    private String tagName;
    private ArrayList<HtmlAttribute> attributes;

    public HtmlSelfClosingElement(String tagName , int line){
        super(line);
        this.tagName = tagName;
        this.attributes = new ArrayList<>();
    }
    public void addAttribute(HtmlAttribute attribute){
        attributes.add(attribute);
    }
    @Override
    public String toString(){
        return "<" + tagName + attributes + "/>";
    }
}
