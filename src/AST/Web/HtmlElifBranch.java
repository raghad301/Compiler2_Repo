package AST.Web;

import java.util.ArrayList;

public class HtmlElifBranch extends HtmlNode {

    private final String condition;
    private final ArrayList<HtmlNode> body;

    public HtmlElifBranch(String condition, int line) {
        super(line);
        this.condition = condition;
        this.body = new ArrayList<>();
    }

    public void addChild(HtmlNode child) {
        if (child != null) {
            body.add(child);
        }
    }

    public String getCondition() {
        return condition;
    }

    public ArrayList<HtmlNode> getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("JinjaElif [")
                .append(condition)
                .append("] (Line: ")
                .append(line)
                .append(")");

        for (HtmlNode child : body) {
            sb.append("\n  |-- ")
                    .append(child.toString().replace("\n", "\n  "));
        }

        return sb.toString();
    }
}