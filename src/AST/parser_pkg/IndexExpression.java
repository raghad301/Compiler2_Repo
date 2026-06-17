package AST.parser_pkg;


import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class IndexExpression extends Expression {

    private Expression object;
    private Expression index;

    public IndexExpression(Expression object, Expression index) {
        this.object = object;
        this.index = index;
    }

    @Override
    public String toString() {
        return object + "[" + index + "]";
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (object != null) children.add(object);
        if (index != null) children.add(index);
        return children;
    }


}
