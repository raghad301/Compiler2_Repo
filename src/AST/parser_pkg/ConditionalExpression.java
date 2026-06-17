package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class ConditionalExpression extends Expression {

    private Expression condition;
    private Expression thenExpr;
    private Expression elseExpr;

    public ConditionalExpression(Expression condition,
                                 Expression thenExpr,
                                 Expression elseExpr) {
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getThenExpr() {
        return thenExpr;
    }

    public Expression getElseExpr() {
        return elseExpr;
    }

    @Override
    public String toString() {
        return "(" + thenExpr + " if " + condition + " else " + elseExpr + ")";
    }

    @Override
    protected List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (condition != null) children.add(condition);
        if (thenExpr != null) children.add(thenExpr);
        if (elseExpr != null) children.add(elseExpr);
        return children;
    }

}
