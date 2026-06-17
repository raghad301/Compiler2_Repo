package AST.parser_pkg;

public class NoneLiteral extends Literal {

    @Override
    public String toString() {
        return "NoneLiteral";
    }

    @Override
    protected String getExtraInfo() {
        return "None";
    }

}
