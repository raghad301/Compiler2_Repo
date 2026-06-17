package Visitor;

import AST.Web.Expression;
import AST.Web.*;
import SymbolTable.WebSymbolTable;
import Web.WebParser;
import Web.WebParserBaseVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class WebVisitor extends WebParserBaseVisitor<Object> {

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



    @Override
    public Object visitHtmlDocument(WebParser.HtmlDocumentContext ctx) {
        HtmlDocument doc = new HtmlDocument(
                ctx.start.getLine()
        );
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
    public Object visitSelfClosingHtmlElement(WebParser.SelfClosingHtmlElementContext ctx) {

        HtmlSelfClosingElement element = new HtmlSelfClosingElement(
                ctx.TAG_NAME().getText(),
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
    public Object visitHtmlContent(WebParser.HtmlContentContext ctx) {
        List<HtmlNode> nodes = new ArrayList<>();


        if (ctx.children != null) {
            for (org.antlr.v4.runtime.tree.ParseTree child : ctx.children) {
                Object visited = visit(child);

                if (visited instanceof HtmlNode) {
                    nodes.add((HtmlNode) visited);
                }

                else if (child instanceof org.antlr.v4.runtime.tree.TerminalNode) {
                    String text = child.getText().trim();
                    if (!text.isEmpty()) {
                        org.antlr.v4.runtime.Token symbol = ((org.antlr.v4.runtime.tree.TerminalNode) child).getSymbol();
                        nodes.add(new HtmlText(
                                child.getText(),
                                symbol.getLine()
                        ));
                    }
                }
            }
        }
        return nodes;
    }


    @Override
    public Object visitNormalHtmlElement(WebParser.NormalHtmlElementContext ctx) {
        HtmlElement element = new HtmlElement(
                ctx.TAG_NAME(0).getText(),
                ctx.start.getLine()
        );


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
    public Object visitJinjaStatement(WebParser.JinjaStatementContext ctx) {
        return visit(ctx.jinjaStatementBody());
    }



    @Override
    public Object visitJinjaSet(WebParser.JinjaSetContext ctx) {
        String varName = ctx.JINJA_NAME().getText();


        Object value = visit(ctx.expression());

        symbolTable.define(varName, "Variable", ctx.getStart().getLine());

        return new AST.Web.JinjaSet(varName, null, ctx.getStart().getLine());
    }


    @Override
    public Object visitVariableExpr(WebParser.VariableExprContext ctx) {
        String varName = ctx.JINJA_NAME(0).getText();




        WebSymbolTable.Symbol symbol = symbolTable.lookup(varName);
        if (symbol != null) {
            // ما نرجع قيمة، بس نسجل إن المتغير موجود
            System.out.println("Symbol Lookup: Found '" + varName + "' [" + symbol.type + "] at line " + symbol.line);
        }

        List<String> names = new ArrayList<>();
        ctx.JINJA_NAME().forEach(n -> names.add(n.getText()));
        return new AST.Web.Variable(
                names,
                ctx.getStart().getLine()
                );
    }

    @Override
    public Object visitNumberLiteral(WebParser.NumberLiteralContext ctx) {
        return (Object)Double.parseDouble(ctx.JINJA_NUMBER().getText());
    }

    @Override
    public Object visitStringLiteral(WebParser.StringLiteralContext ctx) {
        String text = ctx.JINJA_STRING().getText();
        return text.substring(1, text.length() - 1);
    }


    @Override
    public Object visitJinjaExpression(WebParser.JinjaExpressionContext ctx) {

        return visit(ctx.expression());
    }
    @Override
    public Object visitAddSubExpr(WebParser.AddSubExprContext ctx) {

        Object left = visit(ctx.term(0));


        if (ctx.term().size() > 1) {
            Object right = visit(ctx.term(1));
            String operator = ctx.getChild(1).getText();


            if (left instanceof Double && right instanceof Double) {
                double l = (Double) left;
                double r = (Double) right;
                return (Object)(operator.equals("+") ? l + r : l - r);
            }

            Expression leftNode = wrapInLiteral(left);
            Expression rightNode = wrapInLiteral(right);
            return new AST.Web.AddSub(leftNode, operator, rightNode,
                    ctx.getStart().getLine());
        }


        return left;
    }

    private AST.Web.Expression wrapInLiteral(Object value) {
        if (value instanceof Expression) {
            return (AST.Web.Expression) value;
        } else if (value instanceof Double) {

            return new AST.Web.NumberLiteral(value.toString(), 0);
        }
        return null;
    }
    @Override
    public Object visitMulDivExpr(WebParser.MulDivExprContext ctx) {

        Object left = visit(ctx.factor(0));


        if (ctx.factor().size() > 1) {
            Object right = visit(ctx.factor(1));
            String operator = ctx.getChild(1).getText();

            if (left instanceof Double && right instanceof Double) {
                double l = (Double) left;
                double r = (Double) right;
                if (operator.equals("*")) return (Double)(l * r);
                if (operator.equals("/")) return (Object)(r != 0 ? l / r : 0.0);            }
        }
        return left;
    }
    @Override
    public Object visitParenExpr(WebParser.ParenExprContext ctx) {

        return visit(ctx.expression());
    }

    @Override
    public Object visitComparisonExpr(WebParser.ComparisonExprContext ctx) {
        Object left = visit(ctx.simpleExpression(0));

        if (ctx.simpleExpression().size() > 1) {
            Object right = visit(ctx.simpleExpression(1));
            String op = ctx.getChild(1).getText();

            if (left instanceof Double && right instanceof Double) {
                double l = (Double) left;
                double r = (Double) right;
                switch (op) {
                    case "<":  return (Boolean)(l < r);
                    case ">":  return (Boolean)(l > r);
                    case "<=": return (Boolean)(l <= r);
                    case ">=": return (Boolean)(l >= r);
                    case "==": return (Boolean)(l == r);
                    case "!=": return (Boolean)(l != r);
                }
            }
        }
        return left;
    }
    @Override
    public Object visitJinjaIf(WebParser.JinjaIfContext ctx) {
        System.out.println("Found IF statement at line " + ctx.start.getLine());
        enterScope();
        return visitChildren(ctx);
    }
    @Override
    public Object visitJinjaEndIf(WebParser.JinjaEndIfContext ctx) {
        exitScope();
        return visitChildren(ctx);
    }


    @Override
    public Object visitJinjaFor(WebParser.JinjaForContext ctx) {
        enterScope();

        String varName = ctx.JINJA_NAME().getText();
        symbolTable.define(varName, "Loop-Iterator", ctx.getStart().getLine());


        System.out.println("Inside FOR Loop Scope:");
        symbolTable.print();

        Object result = visitChildren(ctx);

        return result;
    }

    @Override
    public Object visitJinjaEndFor(WebParser.JinjaEndForContext ctx) {
        exitScope();
        return visitChildren(ctx);
    }

    public SymbolTable.WebSymbolTable getSymbolTable() {
        return this.symbolTable;
    }


    @Override
    public Object visitAttribute(WebParser.AttributeContext ctx) {
        // اسم الـ attribute مثل: class, href, action, method
        String attrName = ctx.TAG_NAME().getText();
        HtmlAttribute attribute = new HtmlAttribute(
                attrName,
                ctx.start.getLine()
        );

        // اذا عنده قيمة مثل: class="something"
        if (ctx.attributeValue() != null) {
            for (WebParser.AttributeValueContentContext part : ctx.attributeValue().attributeValueContent()) {

                // حالة 1: نص عادي مثل "products.html"
                if (part.ATTVALUE_TEXT() != null) {
                    attribute.addPart(new AttributeText(
                            part.start.getLine(),
                            part.ATTVALUE_TEXT().getText()
                    ));
                }
                // حالة 2: Jinja expression مثل {{ product.name }}
                else if (part.jinjaExpression() != null) {
                    Object expr = visit(part.jinjaExpression());
                    if (expr instanceof JinjaExpression) {
                        attribute.addPart(new AttributeJinjaExpression(
                                (JinjaExpression) expr,
                                part.start.getLine()
                        ));
                    }
                }
                // حالة 3: Jinja statement مثل {% if ... %}
                else if (part.jinjaStatement() != null) {
                    Object stmt = visit(part.jinjaStatement());
                    if (stmt instanceof JinjaStatement) {
                        attribute.addPart(new AttributeJinjaStatement(
                                (JinjaStatement) stmt,
                                part.start.getLine()
                        ));
                    }
                }
            }
        }

        return attribute;
    }

    @Override
    public Object visitStyleElement(WebParser.StyleElementContext ctx) {
        StyleElement style = new StyleElement(
                ctx.start.getLine()
        );

        for (WebParser.StyleContentContext part : ctx.styleContent()) {



            // حالة Jinja expression: {{ variable }}
            if (part.jinjaExpression() != null) {
                Object expr = visit(part.jinjaExpression());
                if (expr instanceof JinjaExpression) {
                    style.addChild(new CssJinjaExpression(
                            (JinjaExpression) expr,
                            part.start.getLine()
                    ));
                }
            }
            // حالة Jinja statement: {% if %} {% for %}
            else if (part.jinjaStatement() != null) {
                Object stmt = visit(part.jinjaStatement());
                if (stmt instanceof JinjaStatement) {
                    style.addChild(new CssJinjaStatement(
                            (JinjaStatement) stmt,
                            part.start.getLine()
                    ));
                }
            }
            // حالة نص CSS عادي: color, px, #fff, ...
            else {
                String text = part.getText();
                if (!text.isBlank()) {
                    style.addChild(new CssText(
                            text,
                            part.start.getLine()
                    ));
                }
            }
        }

        return style;
    }


    @Override
    public Object visitScriptElement(WebParser.ScriptElementContext ctx) {
        ScriptElement script = new ScriptElement(
                ctx.start.getLine()
        );

        for (WebParser.ScriptContentContext part : ctx.scriptContent()) {

            // حالة Jinja expression: {{ variable }}
            if (part.jinjaExpression() != null) {
                Object expr = visit(part.jinjaExpression());
                if (expr instanceof JinjaExpression) {
                    script.addChild(new ScriptJinjaExpression(
                            (JinjaExpression) expr,
                            part.start.getLine()
                    ));
                }
            }
            // حالة Jinja statement: {% if %} {% for %}
            else if (part.jinjaStatement() != null) {
                Object stmt = visit(part.jinjaStatement());
                if (stmt instanceof JinjaStatement) {
                    script.addChild(new ScriptJinjaStatment(
                            (JinjaStatement) stmt,
                            part.start.getLine()
                    ));
                }
            }
            // حالة نص JavaScript عادي
            else {
                String text = part.getText();
                if (!text.isBlank()) {
                    script.addChild(new ScriptText(
                            text,
                            part.start.getLine()
                    ));
                }
            }
        }

        return script;
    }

}
