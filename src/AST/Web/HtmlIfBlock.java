package AST.Web;

import java.util.ArrayList;

public class HtmlIfBlock extends HtmlNode {
    private String condition;
    private ArrayList<HtmlNode> thenBranch;
    private ArrayList<HtmlElifBranch> elifBranches;
    private ArrayList<HtmlNode> elseBranch;

    public HtmlIfBlock(String condition, int line) {
        super(line);
        this.condition = condition;
        this.thenBranch = new ArrayList<>();
        this.elifBranches = new ArrayList<>();
        this.elseBranch = new ArrayList<>();
    }

    public void addThenChild(HtmlNode child) {
        thenBranch.add(child);
    }

    public void addElifBranch(HtmlElifBranch branch) {
        if (branch != null) {
            elifBranches.add(branch);
        }
    }

    public void addElseChild(HtmlNode child) {
        elseBranch.add(child);
    }

    public String getCondition() { return condition; }

    public ArrayList<HtmlNode> getThenBranch() { return thenBranch; }


    public ArrayList<HtmlElifBranch> getElifBranches() {
        return elifBranches;
    }

    public ArrayList<HtmlNode> getElseBranch() { return elseBranch; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("JinjaIf [")
                .append(condition)
                .append("] (Line: ")
                .append(line)
                .append(")");

        for (HtmlNode child : thenBranch) {
            sb.append("\n  |-- ")
                    .append(child.toString().replace("\n", "\n  "));
        }

        for (HtmlElifBranch elifBranch : elifBranches) {
            sb.append("\n  |-- ")
                    .append(elifBranch.toString().replace("\n", "\n  "));
        }

        if (!elseBranch.isEmpty()) {
            sb.append("\n  |-- ElseBranch:");

            for (HtmlNode child : elseBranch) {
                sb.append("\n    |-- ")
                        .append(child.toString().replace("\n", "\n    "));
            }
        }

        return sb.toString();
    }
}