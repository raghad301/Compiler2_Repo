package AST.parser_pkg;

import AST.ASTNode;

public class Parameter extends ASTNode {

    public String name;

    public Parameter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected String getExtraInfo() {
        return name;
    }
}
