package AST.parser_pkg;

public class StringLiteral extends Literal {

    private final String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringLiteral(\"" + value + "\")";
    }

    @Override
    protected String getExtraInfo() {
        return "\"" + value + "\"";
    }

}
