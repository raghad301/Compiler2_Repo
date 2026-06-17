package AST.parser_pkg;

import AST.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class ArgumentList extends ASTNode {
    private final List<Argument> arguments;

    public ArgumentList() {
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Argument argument) {
        this.arguments.add(argument);
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    protected List<ASTNode> getChildren() {
        return new ArrayList<>(arguments);
    }
}

