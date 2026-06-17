package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Decorator extends ASTNode {

    private List<String> nameParts;

    private List<Expression> arguments;

    public Decorator(List<String> nameParts, List<Expression> arguments) {
        this.nameParts = nameParts;
        this.arguments = arguments;
    }

    public List<String> getNameParts() {
        return nameParts;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String getExtraInfo() {
        return "@" + String.join(".", nameParts);
    }

    @Override
    protected List<ASTNode> getChildren() {
        return (arguments != null) ? new ArrayList<>(arguments) : Collections.emptyList();
    }

}
