package AST.parser_pkg;

import AST.Expression;

public class Identifier extends Expression {

    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Identifier(" + name + ")";
    }

    @Override
    protected String getExtraInfo() {
        return name;
    }

}
