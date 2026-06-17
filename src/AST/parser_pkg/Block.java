package AST.parser_pkg;

import AST.ASTNode;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class Block extends ASTNode {

    private final List<Statement> statements;

    public Block() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(Statement stmt) {
        this.statements.add(stmt);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Statement s : statements) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    @Override
    protected List<ASTNode> getChildren() {
        return new ArrayList<>(statements);
    }

}
