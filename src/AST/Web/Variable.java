package AST.Web;

import java.util.List;

public class Variable extends AST.Web.Expression {
    private List<String>names;

    public Variable(List<String> names, int line){
        super(line);
        this.names = names;
    }
    @Override
    public String toString() {
        return "VariableNode [" + String.join(".", names) + "] (Line: " + line + ")";
    }



}
