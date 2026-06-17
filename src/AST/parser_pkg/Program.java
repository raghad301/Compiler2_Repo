package AST.parser_pkg;

import AST.ASTNode;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;
public class Program extends ASTNode {
    private final List<Statement> statements;

    public Program() {
        this.statements = new ArrayList<>();
    }

    public void addStatement(Statement stmt) {
        this.statements.add(stmt);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(statements);
    }

}

