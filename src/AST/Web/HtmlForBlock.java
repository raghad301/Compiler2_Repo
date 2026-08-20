package AST.Web;

import java.util.ArrayList;

public class HtmlForBlock extends HtmlNode {
    private String loopVar;
    private String iterable;
    private ArrayList<HtmlNode> body;

    public HtmlForBlock(String loopVar, String iterable, int line) {
        super(line);
        this.loopVar = loopVar;
        this.iterable = iterable;
        this.body = new ArrayList<>();
    }

    public void addChild(HtmlNode child) {
        body.add(child);
    }

    public String getLoopVar() { return loopVar; }
    public String getIterable() { return iterable; }
    public ArrayList<HtmlNode> getBody() { return body; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JinjaForLoop [").append(loopVar).append(" in ").append(iterable).append("] (Line: ").append(line).append(")");
        for (HtmlNode child : body) {
            sb.append("\n  |-- ").append(child.toString().replace("\n", "\n  "));
        }
        return sb.toString();
    }
}