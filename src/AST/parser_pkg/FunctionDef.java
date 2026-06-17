package AST.parser_pkg;

import AST.ASTNode;
import AST.Statement;

import java.util.ArrayList;
import java.util.List;

public class FunctionDef extends Statement {

    public String name;
    public ParameterList parameters;
    public Block body;
    public List<Decorator> decorators;

    public FunctionDef(String name) {
        this.name = name;
        this.decorators = new ArrayList<>();
    }

    public void addDecorator(Decorator decorator) {
        this.decorators.add(decorator);
    }

    // ✅ هاتين كانتا ناقصتين
    public void setParameters(ParameterList params) {
        this.parameters = params;
    }

    public void setBody(Block body) {
        this.body = body;
    }

    @Override
    public String getExtraInfo() {
        return name;
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.addAll(decorators);
        if (parameters != null) children.add(parameters);
        if (body != null) children.add(body);
        return children;
    }
}