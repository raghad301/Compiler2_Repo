package AST;

import java.util.Collections;
import java.util.List;

public abstract class ASTNode {
    protected int lineNumber;

    public void setLineNumber(int line) {
        this.lineNumber = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void print(String indent) {
        String className = this.getClass().getSimpleName();

        System.out.print(indent + "|-- " + className);

        String extra = getExtraInfo();
        if (extra != null && !extra.isEmpty()) {
            System.out.print(": " + extra);
        }

        System.out.println(" (Line" + lineNumber + ")");

        List<ASTNode> children = getChildren();
        if (children != null) {
            for (ASTNode child : children) {
                if (child != null) {
                    child.print(indent + "  ");
                }
            }
        }
    }

    protected String getExtraInfo() {
        return "";
    }

    protected List<ASTNode> getChildren() {
        return Collections.emptyList();
    }
}