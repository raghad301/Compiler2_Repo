package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpression extends Expression {

    protected Expression left;
    protected Expression right;
    protected String operator;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    protected String getExtraInfo() {
        return operator;
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (left != null) children.add(left);
        if (right != null) children.add(right);
        return children;
    }
}
