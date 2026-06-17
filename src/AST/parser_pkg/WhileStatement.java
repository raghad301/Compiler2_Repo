package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class WhileStatement extends Statement {

    private Expression condition;
    private Block body;

    public WhileStatement(Expression condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (condition != null) children.add(condition);
        if (body != null) children.add(body);
        return children;
    }

}
