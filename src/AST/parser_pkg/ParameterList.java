package AST.parser_pkg;

import AST.ASTNode;

import java.util.ArrayList;
import java.util.List;

public class ParameterList extends ASTNode {

    public List<Parameter> parameters;

    public ParameterList() {
        this.parameters = new ArrayList<>();
    }

    public void addParameter(Parameter p) {
        parameters.add(p);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return parameters.toString();
    }

    @Override
    public List<ASTNode> getChildren() {
        return new ArrayList<>(parameters);
    }

}
