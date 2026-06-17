package AST.Web;

public class ScriptJinjaStatment extends ScriptNode {
    private JinjaStatement statement;

    public ScriptJinjaStatment(JinjaStatement statement, int line) {
        super(line);
        this.statement = statement;
    }

    public JinjaStatement getStatement() {
        return statement;
    }
    @Override
    public String toString() {
        return "{%" + statement + "%}";
    }
}
