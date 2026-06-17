package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class ElifPart extends ASTNode {

    private Expression condition;
    private Block block;

    public ElifPart(Expression condition, Block block) {
        this.condition = condition;
        this.block = block;
    }

    public Expression getCondition() {
        return condition;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (condition != null) children.add(condition);
        if (block != null) children.add(block);
        return children;
    }
}
