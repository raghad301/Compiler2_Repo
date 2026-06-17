package AST.parser_pkg;
import AST.ASTNode;
import AST.Expression;

import java.util.Collections;
import java.util.List;

public class IndexAccess extends TargetAccess {

    private Expression index;

    public IndexAccess(Expression index) {
        this.index = index;
    }

    public Expression getIndex() {
        return index;
    }

    @Override
    public List<ASTNode> getChildren() {
        return (index != null) ? Collections.singletonList(index) : Collections.emptyList();
    }

}

