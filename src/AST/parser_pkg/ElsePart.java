package AST.parser_pkg;

import AST.ASTNode;

import java.util.Collections;
import java.util.List;

public class ElsePart extends ASTNode {

    private Block block;

    public ElsePart(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public List<ASTNode> getChildren() {
        return (block != null) ? Collections.singletonList(block) : Collections.emptyList();
    }
}
