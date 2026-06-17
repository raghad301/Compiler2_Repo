package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class Assignment extends Statement {

    private Target target;
    private Expression value;

    public Assignment(Target target, Expression value) {
        this.target = target;
        this.value = value;
    }

    public Target getTarget() {
        return target;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    protected String getExtraInfo() {
        return (target != null) ? target.toString() : "";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (target instanceof ASTNode) children.add((ASTNode) target);
        if (value != null) children.add(value);
        return children;
    }
}
