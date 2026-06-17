package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.List;

public class ListExpression extends Expression {

    private final List<Expression> elements;

    private final String loopVariable;
    private final Expression iterable;
    private final Expression condition;

    public ListExpression(List<Expression> elements) {
        this.elements = elements;
        this.loopVariable = null;
        this.iterable = null;
        this.condition = null;
    }

    public ListExpression(
            Expression element,
            String loopVariable,
            Expression iterable,
            Expression condition
    ) {
        this.elements = List.of(element);
        this.loopVariable = loopVariable;
        this.iterable = iterable;
        this.condition = condition;
    }

    public List<Expression> getElements() {
        return elements;
    }

    public String getLoopVariable() {
        return loopVariable;
    }

    public Expression getIterable() {
        return iterable;
    }

    public Expression getCondition() {
        return condition;
    }

    @Override
    protected String getExtraInfo() {
        return (loopVariable != null) ? "Comprehension (for " + loopVariable + ")" : "Size: " + elements.size();
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>(elements);
        if (iterable != null) children.add(iterable);
        if (condition != null) children.add(condition);
        return children;
    }

}
