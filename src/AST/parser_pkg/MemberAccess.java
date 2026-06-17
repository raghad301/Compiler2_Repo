package AST.parser_pkg;

import AST.ASTNode;
import AST.Expression;

import java.util.Collections;
import java.util.List;

public class MemberAccess extends Expression {

    private Expression object;
    private String memberName;

    public MemberAccess(Expression object, String memberName) {
        this.object = object;
        this.memberName = memberName;
    }

    public Expression getObject() {
        return object;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public String toString() {
        return object.toString() + "." + memberName;
    }

    @Override
    protected String getExtraInfo() {
        return "." + memberName;
    }

    @Override
    public List<ASTNode> getChildren() {
        return (object != null) ? Collections.singletonList(object) : Collections.emptyList();
    }

}
