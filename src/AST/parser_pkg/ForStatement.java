package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class ForStatement extends Statement {

    private String iterator;
    private Expression iterable;
    private Block body;

    public ForStatement(String iterator, Expression iterable, Block body) {
        this.iterator = iterator;
        this.iterable = iterable;
        this.body = body;
    }

    public String getIterator() {
        return iterator;
    }

    public Expression getIterable() {
        return iterable;
    }

    public Block getBody() {
        return body;
    }

    @Override
    protected String getExtraInfo() {
        return "Iter: " + iterator;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (iterable != null) children.add(iterable);
        if (body != null) children.add(body);
        return children;
    }
}
