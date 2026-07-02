package Semantic;

public class SemanticError {
    private final String message;
    private final int line;

    public SemanticError(String message, int line) {
        this.message = message;
        this.line = line;
    }

    public void report() {
        System.err.println("Semantic Error at line " + line + ": " + message);
    }

    public String getMessage() { return message; }
    public int getLine()       { return line; }

    @Override
    public String toString() {
        return "SemanticError(line=" + line + ", msg=\"" + message + "\")";
    }
}
