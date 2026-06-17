package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class IfStatement extends Statement {

    private Expression condition;
    private Block thenBlock;
    private List<ElifPart> elifParts;
    private ElsePart elsePart;

    public IfStatement(Expression condition,
                       Block thenBlock,
                       List<ElifPart> elifParts,
                       ElsePart elsePart) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elifParts = elifParts;
        this.elsePart = elsePart;
    }

    public Expression getCondition() {
        return condition;
    }

    public Block getThenBlock() {
        return thenBlock;
    }

    public List<ElifPart> getElifParts() {
        return elifParts;
    }

    public ElsePart getElsePart() {
        return elsePart;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (condition != null) children.add(condition);
        if (thenBlock != null) children.add(thenBlock);
        if (elifParts != null) children.addAll(elifParts);
        if (elsePart != null) children.add(elsePart);
        return children;
    }
}
