package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class DictEntry extends ASTNode {

    private final Expression key;
    private final Expression value;

    public DictEntry(Expression key, Expression value, int line) {
        this.key = key;
        this.value = value;
        this.setLineNumber(line);
    }

    public Expression getKey() { return key; }
    public Expression getValue() { return value; }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (key != null) children.add(key);
        if (value != null) children.add(value);
        return children;
    }
}