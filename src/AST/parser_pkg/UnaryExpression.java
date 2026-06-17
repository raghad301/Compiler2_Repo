package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.Collections;
import java.util.List;

public class UnaryExpression extends Expression {

    protected String operator;
    protected Expression operand;

    public UnaryExpression(String operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getOperand() {
        return operand;
    }

    @Override
    protected String getExtraInfo() {
        return operator;
    }

    @Override
    public List<ASTNode> getChildren() {
        return (operand != null) ? Collections.singletonList(operand) : Collections.emptyList();
    }

}
