package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.Collections;
import java.util.List;

public class ExpressionStatement extends Statement {

    private Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "ExpressionStatement(" + expression + ")";
    }

    @Override
    public List<ASTNode> getChildren() {
        return (expression != null) ? Collections.singletonList(expression) : Collections.emptyList();
    }
}
