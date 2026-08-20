package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionCall extends Expression {

    private final Expression callee;
    private final List<Argument> argumentNodes;

    public FunctionCall(Expression callee) {
        this.callee = callee;
        this.argumentNodes = new ArrayList<>();
    }

    public FunctionCall(
            Expression callee,
            List<Expression> arguments
    ) {
        this(callee);

        if (arguments != null) {
            for (Expression argument : arguments) {
                addArgument(argument);
            }
        }
    }

    public void addArgument(Expression argument) {
        if (argument != null) {
            argumentNodes.add(
                    new Argument(argument)
            );
        }
    }

    public void addArgument(Argument argument) {
        if (argument != null) {
            argumentNodes.add(argument);
        }
    }

    public Expression getCallee() {
        return callee;
    }

    public List<Expression> getArguments() {
        List<Expression> values = new ArrayList<>();

        for (Argument argument : argumentNodes) {
            values.add(argument.getValue());
        }

        return values;
    }

    public List<Argument> getArgumentNodes() {
        return Collections.unmodifiableList(
                argumentNodes
        );
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();

        if (callee != null) {
            children.add(callee);
        }

        children.addAll(argumentNodes);

        return children;
    }
}