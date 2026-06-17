package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.Collections;
import java.util.List;

public class Argument extends ASTNode {

    private String name;
    private Expression value;

    public Argument(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public Argument(Expression value) {
        this(null, value);
    }

    public boolean isKeyword() {
        return name != null;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    protected String getExtraInfo() {
        return isKeyword() ? "Keyword: " + name : "Positional";
    }

    @Override
    protected List<ASTNode> getChildren() {
        return Collections.singletonList(value);
    }

}
