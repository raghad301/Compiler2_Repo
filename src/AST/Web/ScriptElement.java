package AST.Web;

import java.util.ArrayList;

public class ScriptElement extends ScriptNode {

    private ArrayList<ScriptNode> children = new ArrayList<>();
    private String content;
    private String openingTag = "<script>";

    public ScriptElement(int line) {
        super(line);
        this.children = new ArrayList<>();
    }

    public ScriptElement(String content, int line) {
        super(line);
        this.content = content;
        this.children = new ArrayList<>();
    }

    public void addChild(ScriptNode node) {
        children.add(node);
    }

    public ArrayList<ScriptNode> getChildren() {
        return children;
    }

    public String getContent() {
        return content;
    }

    public String getOpeningTag() {
        return openingTag;
    }

    public void setOpeningTag(String openingTag) {
        if (openingTag != null && !openingTag.isBlank()) {
            this.openingTag = openingTag;
        }
    }

    @Override
    public String toString() {
        return "ScriptElement (Line: " + line + ")";
    }
}
