package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.Collections;
import java.util.List;

public class ReturnStatement extends Statement {

    private Expression value;

    public ReturnStatement(Expression value) {
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public List<ASTNode> getChildren() {
        return (value != null) ? Collections.singletonList(value) : Collections.emptyList();
    }

}
