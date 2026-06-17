package AST.parser_pkg;

import AST.Expression;

public class NumberLiteral extends Expression {

    private double value;

    public NumberLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    protected String getExtraInfo() {
        return String.valueOf(value);
    }

}
