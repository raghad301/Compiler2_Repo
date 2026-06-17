package AST.parser_pkg;

import AST.Expression;

public class GenericBinaryExpression extends BinaryExpression {

    public GenericBinaryExpression(Expression left, String operator, Expression right) {
        super(left, operator, right);
    }


}
