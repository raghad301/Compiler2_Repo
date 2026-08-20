package Visitor;

import AST.Web.*;
import SymbolTable.WebSymbolTable;
import Web.WebParser;
import Web.WebParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;

public class WebVisitor extends WebParserBaseVisitor<Object> {

    private String sourceText(ParserRuleContext ctx) {
        if (ctx == null || ctx.getStart() == null || ctx.getStop() == null) {
            return "";
        }

        return ctx.getStart()
                .getInputStream()
                .getText(Interval.of(
                        ctx.getStart().getStartIndex(),
                        ctx.getStop().getStopIndex()
                ))
                .trim();
    }

    private WebSymbolTable symbolTable = new WebSymbolTable();

    private void enterScope() {
        symbolTable = new WebSymbolTable(symbolTable);
        System.out.println(">>> Entering New Scope");
    }

    private void exitScope() {
        if (symbolTable.getParent() != null) {
            System.out.println("<<< Exiting Scope. Local variables cleared.");
            symbolTable = symbolTable.getParent();
        }
    }

    public WebSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    @Override
    public Object visitHtmlDocument(WebParser.HtmlDocumentContext ctx) {
        HtmlDocument doc = new HtmlDocument(ctx.start.getLine());
        Object content = visit(ctx.htmlContent());

        if (content instanceof List) {
            for (Object node : (List<?>) content) {
                if (node instanceof HtmlNode) {
                    doc.addChild((HtmlNode) node);
                }
            }
        }
        return doc;
    }

    @Override
    public Object visitNormalHtmlElement(WebParser.NormalHtmlElementContext ctx) {
        HtmlElement element = new HtmlElement(
                ctx.TAG_NAME(0).getText(),
                ctx.start.getLine()
        );

        if (ctx.attribute() != null) {
            for (WebParser.AttributeContext attrCtx : ctx.attribute()) {
                Object attr = visit(attrCtx);
                if (attr instanceof HtmlAttribute) {
                    element.addAttribute((HtmlAttribute) attr);
                }
            }
        }

        Object content = visit(ctx.htmlContent());
        if (content instanceof List) {
            for (Object node : (List<?>) content) {
                if (node instanceof HtmlNode) {
                    element.addChild((HtmlNode) node);
                }
            }
        }
        return element;
    }

    @Override
    public Object visitSelfClosingHtmlElement(WebParser.SelfClosingHtmlElementContext ctx) {
        String tagName = ctx.TAG_NAME() != null ? ctx.TAG_NAME().getText() : ctx.VOID_TAG_NAME().getText();
        HtmlSelfClosingElement element = new HtmlSelfClosingElement(
                tagName,
                ctx.start.getLine()
        );

        if (ctx.attribute() != null) {
            for (WebParser.AttributeContext attrCtx : ctx.attribute()) {
                Object attr = visit(attrCtx);
                if (attr instanceof HtmlAttribute) {
                    element.addAttribute((HtmlAttribute) attr);
                }
            }
        }
        return element;
    }

    @Override
    public Object visitVoidHtmlElement(WebParser.VoidHtmlElementContext ctx) {
        HtmlSelfClosingElement element = new HtmlSelfClosingElement(
                ctx.VOID_TAG_NAME().getText(), ctx.start.getLine());
        for (WebParser.AttributeContext attrCtx : ctx.attribute()) {
            Object attr = visit(attrCtx);
            if (attr instanceof HtmlAttribute) element.addAttribute((HtmlAttribute) attr);
        }
        return element;
    }

    @Override
    public Object visitHtmlNode(WebParser.HtmlNodeContext ctx) {

        if (ctx.HTML_TEXT() != null) {
            String text = ctx.HTML_TEXT().getText();

            if (text.trim().isEmpty()) {
                return null;
            }

            return new HtmlText(
                    text,
                    ctx.HTML_TEXT().getSymbol().getLine()
            );
        }

        if (ctx.DOCTYPE() != null) {
            return new HtmlText(
                    ctx.DOCTYPE().getText(),
                    ctx.DOCTYPE().getSymbol().getLine()
            );
        }

        if (ctx.HTML_COMMENT() != null) {
            return new HtmlText(
                    ctx.HTML_COMMENT().getText(),
                    ctx.HTML_COMMENT().getSymbol().getLine()
            );
        }

        return visitChildren(ctx);
    }

    @Override
    public Object visitHtmlContent(WebParser.HtmlContentContext ctx) {
        List<HtmlNode> nodes = new ArrayList<>();

        if (ctx.children != null) {
            for (org.antlr.v4.runtime.tree.ParseTree child : ctx.children) {
                Object visited = visit(child);

                if (visited instanceof HtmlNode) {
                    nodes.add((HtmlNode) visited);
                } else if (visited instanceof List<?>) {
                    for (Object item : (List<?>) visited) {
                        if (item instanceof HtmlNode) {
                            nodes.add((HtmlNode) item);
                        }
                    }
                } else if (child instanceof org.antlr.v4.runtime.tree.TerminalNode) {
                    String text = child.getText().trim();
                    if (!text.isEmpty()) {
                        org.antlr.v4.runtime.Token symbol = ((org.antlr.v4.runtime.tree.TerminalNode) child).getSymbol();
                        nodes.add(new HtmlText(child.getText(), symbol.getLine()));
                    }
                }
            }
        }
        return nodes;
    }

    @Override
    public Object visitJinjaStatement(WebParser.JinjaStatementContext ctx) {
        return visit(ctx.jinjaStatementBody());
    }

    @Override
    public Object visitJinjaSet(WebParser.JinjaSetContext ctx) {
        String varName = ctx.JINJA_NAME().getText();
        String exprText = ctx.expression() != null ? sourceText(ctx.expression()) : "";
        symbolTable.define(varName, "Variable", ctx.getStart().getLine());

        return new HtmlSetStatement(varName, exprText, ctx.getStart().getLine());
    }

    @Override
    public Object visitJinjaExpression(WebParser.JinjaExpressionContext ctx) {
        String exprText = ctx.expression() != null ? sourceText(ctx.expression()) : "";        return new HtmlExpressionBlock(exprText, ctx.getStart().getLine());
    }

    private List<HtmlNode> collectHtmlNodes(
            WebParser.HtmlContentContext contentContext
    ) {
        List<HtmlNode> nodes = new ArrayList<>();

        if (contentContext == null) {
            return nodes;
        }

        Object visited = visit(contentContext);

        if (visited instanceof HtmlNode) {
            nodes.add((HtmlNode) visited);
        } else if (visited instanceof List<?>) {
            for (Object item : (List<?>) visited) {
                if (item instanceof HtmlNode) {
                    nodes.add((HtmlNode) item);
                }
            }
        }

        return nodes;
    }

    @Override
    public Object visitJinjaSetStatement(
            WebParser.JinjaSetStatementContext ctx
    ) {
        String variableName = ctx.JINJA_NAME().getText();
        String expressionText = sourceText(ctx.expression());
        symbolTable.define(
                variableName,
                "Variable",
                ctx.getStart().getLine()
        );

        return new HtmlSetStatement(
                variableName,
                expressionText,
                ctx.getStart().getLine()
        );
    }

    @Override
    public Object visitJinjaIfBlock(
            WebParser.JinjaIfBlockContext ctx
    ) {
        String condition = sourceText(ctx.condition);
        HtmlIfBlock ifBlock = new HtmlIfBlock(
                condition,
                ctx.getStart().getLine()
        );

        enterScope();

        for (HtmlNode child : collectHtmlNodes(ctx.thenBody)) {
            ifBlock.addThenChild(child);
        }

        for (WebParser.JinjaElifClauseContext elifContext
                : ctx.jinjaElifClause()) {

            HtmlElifBranch elifBranch = new HtmlElifBranch(
                    sourceText(elifContext.condition),
                    elifContext.getStart().getLine()
            );

            for (HtmlNode child
                    : collectHtmlNodes(elifContext.body)) {
                elifBranch.addChild(child);
            }

            ifBlock.addElifBranch(elifBranch);
        }

        if (ctx.jinjaElseClause() != null) {
            WebParser.JinjaElseClauseContext elseContext =
                    ctx.jinjaElseClause();

            for (HtmlNode child
                    : collectHtmlNodes(elseContext.body)) {
                ifBlock.addElseChild(child);
            }
        }

        exitScope();

        return ifBlock;
    }

    @Override
    public Object visitJinjaForBlock(
            WebParser.JinjaForBlockContext ctx
    ) {
        String variableName = ctx.variable.getText();
        String iterableExpression = sourceText(ctx.iterable);

        HtmlForBlock forBlock = new HtmlForBlock(
                variableName,
                iterableExpression,
                ctx.getStart().getLine()
        );

        enterScope();

        symbolTable.define(
                variableName,
                "Loop-Iterator",
                ctx.getStart().getLine()
        );

        for (HtmlNode child : collectHtmlNodes(ctx.body)) {
            forBlock.addChild(child);
        }

        exitScope();

        return forBlock;
    }

    @Override
    public Object visitJinjaIf(WebParser.JinjaIfContext ctx) {
        System.out.println("Found IF statement at line " + ctx.start.getLine());
        enterScope();

        String condition = ctx.expression() != null ? sourceText(ctx.expression()) : "";
        HtmlIfBlock ifBlock = new HtmlIfBlock(condition, ctx.getStart().getLine());

        boolean inElseBranch = false;
        if (ctx.children != null) {
            for (org.antlr.v4.runtime.tree.ParseTree child : ctx.children) {
                String childText = child.getText();
                if (childText.startsWith("{% else") || childText.startsWith("{%else")) {
                    inElseBranch = true;
                    continue;
                }

                Object visited = visit(child);
                if (visited instanceof HtmlNode) {
                    if (!inElseBranch) {
                        ifBlock.addThenChild((HtmlNode) visited);
                    } else {
                        ifBlock.addElseChild((HtmlNode) visited);
                    }
                } else if (visited instanceof List<?>) {
                    for (Object item : (List<?>) visited) {
                        if (item instanceof HtmlNode) {
                            if (!inElseBranch) {
                                ifBlock.addThenChild((HtmlNode) item);
                            } else {
                                ifBlock.addElseChild((HtmlNode) item);
                            }
                        }
                    }
                }
            }
        }

        exitScope();
        return ifBlock;
    }

    @Override
    public Object visitJinjaFor(WebParser.JinjaForContext ctx) {
        enterScope();

        String varName = ctx.JINJA_NAME().getText();
        String iterable = ctx.expression() != null ? sourceText(ctx.expression()) : "";
        symbolTable.define(varName, "Loop-Iterator", ctx.getStart().getLine());

        System.out.println("Inside FOR Loop Scope:");
        symbolTable.print();

        HtmlForBlock forBlock = new HtmlForBlock(varName, iterable, ctx.getStart().getLine());

        if (ctx.children != null) {
            for (org.antlr.v4.runtime.tree.ParseTree child : ctx.children) {
                Object visited = visit(child);
                if (visited instanceof HtmlNode) {
                    forBlock.addChild((HtmlNode) visited);
                } else if (visited instanceof List<?>) {
                    for (Object item : (List<?>) visited) {
                        if (item instanceof HtmlNode) {
                            forBlock.addChild((HtmlNode) item);
                        }
                    }
                }
            }
        }

        exitScope();
        return forBlock;
    }

    @Override
    public Object visitAttribute(WebParser.AttributeContext ctx) {

        String attributeName;

        if (ctx.TAG_NAME() != null) {
            attributeName = ctx.TAG_NAME().getText();
        } else {
            attributeName = ctx.JINJA_NAME().getText();
        }

        HtmlAttribute attribute = new HtmlAttribute(
                attributeName,
                ctx.getStart().getLine()
        );

        if (ctx.attributeValue() == null) {
            return attribute;
        }

        for (WebParser.AttributeValueContentContext part
                : ctx.attributeValue().attributeValueContent()) {

            if (part.ATTVALUE_TEXT() != null) {
                attribute.addPart(
                        new AttributeText(
                                part.getStart().getLine(),
                                part.ATTVALUE_TEXT().getText()
                        )
                );

                continue;
            }

            if (part.jinjaExpression() != null) {
                Object expressionNode =
                        visit(part.jinjaExpression());

                if (expressionNode instanceof HtmlExpressionBlock) {
                    attribute.addPart(
                            new AttributeJinjaExpression(
                                    (HtmlExpressionBlock) expressionNode,
                                    part.getStart().getLine()
                            )
                    );
                }
            }
        }

        return attribute;
    }

    @Override
    public Object visitStyleElement(WebParser.StyleElementContext ctx) {
        StyleElement style = new StyleElement(ctx.start.getLine());

        for (WebParser.StyleContentContext part : ctx.styleContent()) {
            if (part.jinjaExpression() != null) {
                Object expr = visit(part.jinjaExpression());
                if (expr instanceof JinjaExpression) {
                    style.addChild(new CssJinjaExpression((JinjaExpression) expr, part.start.getLine()));
                }
            } else if (part.jinjaStatement() != null) {
                Object stmt = visit(part.jinjaStatement());
                if (stmt instanceof JinjaStatement) {
                    style.addChild(new CssJinjaStatement((JinjaStatement) stmt, part.start.getLine()));
                }
            } else {
                String text = part.getText();
                if (!text.isBlank()) {
                    style.addChild(new CssText(text, part.start.getLine()));
                }
            }
        }
        return style;
    }

    @Override
    public Object visitScriptElement(WebParser.ScriptElementContext ctx) {
        ScriptElement script = new ScriptElement(ctx.start.getLine());
        script.setOpeningTag(ctx.SCRIPT_OPEN().getText());

        for (WebParser.ScriptContentContext part : ctx.scriptContent()) {
            if (part.jinjaExpression() != null) {
                Object expr = visit(part.jinjaExpression());
                if (expr instanceof JinjaExpression) {
                    script.addChild(new ScriptJinjaExpression((JinjaExpression) expr, part.start.getLine()));
                }
            } else if (part.jinjaStatement() != null) {
                Object stmt = visit(part.jinjaStatement());
                if (stmt instanceof JinjaStatement) {
                    script.addChild(new ScriptJinjaStatment((JinjaStatement) stmt, part.start.getLine()));
                }
            } else {
                String text = part.getText();
                if (!text.isBlank()) {
                    script.addChild(new ScriptText(text, part.start.getLine()));
                }
            }
        }
        return script;
    }

    @Override
    public Object visitVariableExpr(WebParser.VariableExprContext ctx) {
        String varName = ctx.JINJA_NAME(0).getText();
        WebSymbolTable.Symbol symbol = symbolTable.lookup(varName);
        if (symbol != null) {
            System.out.println("Symbol Lookup: Found '" + varName + "' [" + symbol.type + "] at line " + symbol.line);
        }
        List<String> names = new ArrayList<>();
        ctx.JINJA_NAME().forEach(n -> names.add(n.getText()));
        return new AST.Web.Variable(names, ctx.getStart().getLine());
    }

    @Override
    public Object visitNumberLiteral(WebParser.NumberLiteralContext ctx) {
        return Double.parseDouble(ctx.JINJA_NUMBER().getText());
    }

    @Override
    public Object visitStringLiteral(WebParser.StringLiteralContext ctx) {
        String text = ctx.JINJA_STRING().getText();
        return text.substring(1, text.length() - 1);
    }
}
