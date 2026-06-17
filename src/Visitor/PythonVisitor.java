package Visitor;

import AST.ASTNode;
import AST.Statement;
import AST.parser_pkg.*;
import SymbolTable.PythonSymbolTable;
import org.antlr.v4.runtime.tree.ParseTree;
import AST.Expression;
import parser_pkg.pythonParser;
import parser_pkg.pythonParserBaseVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class PythonVisitor extends pythonParserBaseVisitor<ASTNode> {

    private Set<String> comprehensionVars = new HashSet<>();
    private PythonSymbolTable symbolTable = new PythonSymbolTable();

    @Override
    public ASTNode visit(ParseTree tree) {
        if (tree == null)
            return null;
        ASTNode result = super.visit(tree);
        if (result == null) {
            System.err.println("Warning: Visitor returned null for node: " + tree.getText());
        }
        return result;
    }

    @Override
    public ASTNode visitProgram(pythonParser.ProgramContext ctx) {
        Program program = new Program();
        program.setLineNumber(ctx.getStart().getLine());

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            Statement stmt = (Statement) visit(stmtCtx);
            if (stmt != null) {
                program.addStatement(stmt);
            }
        }
        System.out.println("=== Final Global Symbol Table ===");
        symbolTable.printScope("Global");

        return program;
    }

    @Override
    public ASTNode visitStatement(pythonParser.StatementContext ctx) {
        if (ctx.compound_stmt() != null) {
            return visit(ctx.compound_stmt());
        }
        if (ctx.simple_stmt() != null) {
            return visit(ctx.simple_stmt());
        }
        return null;
    }

    @Override
    public ASTNode visitSimple_stmt(pythonParser.Simple_stmtContext ctx) {

        if (ctx.importStatement() != null) {
            return visit(ctx.importStatement());
        }
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        }
        if (ctx.returnStatement() != null) {
            return visit(ctx.returnStatement());
        }
        if (ctx.expressionStatement() != null) {
            return visit(ctx.expressionStatement());
        }
        if (ctx.logic_expr() != null) {
            return visit(ctx.logic_expr());
        }
        return null;
    }

    @Override
    public ASTNode visitCompound_stmt(pythonParser.Compound_stmtContext ctx) {
        if (ctx.ifStatement() != null)
            return visit(ctx.ifStatement());
        if (ctx.whileStatement() != null)
            return visit(ctx.whileStatement());
        if (ctx.forStatement() != null)
            return visit(ctx.forStatement());
        if (ctx.functionDef() != null)
            return visit(ctx.functionDef());

        return null;
    }

    @Override
    public ASTNode visitReturnStatement(pythonParser.ReturnStatementContext ctx) {
        Expression value = null;
        if (ctx.testList() != null) {
            ASTNode node = visit(ctx.testList());
            if (node instanceof Expression) {
                value = (Expression) node;
            }
        }

        ReturnStatement returnStmt = new ReturnStatement(value);
        returnStmt.setLineNumber(ctx.getStart().getLine());
        return returnStmt;
    }

    @Override
    public ASTNode visitBlock(pythonParser.BlockContext ctx) {
        Block block = new Block();
        block.setLineNumber(ctx.getStart().getLine());

        for (pythonParser.StatementContext stmtCtx : ctx.statement()) {
            ASTNode node = visit(stmtCtx);

            if (node == null) {
                System.out.println(
                        "Warning: Visitor returned null for statement at line "
                                + stmtCtx.getStart().getLine());
                continue;
            }

            if (node instanceof Statement) {
                block.addStatement((Statement) node);
            } else {
                System.out.println(
                        "Warning: Node is not a Statement: "
                                + node.getClass().getSimpleName());
            }
        }
        return block;
    }

    @Override
    public ASTNode visitDecorator(pythonParser.DecoratorContext ctx) {
        List<String> nameParts = new ArrayList<>();
        for (int i = 0; i < ctx.ID().size(); i++) {
            String currentID = ctx.ID(i).getText();
            nameParts.add(ctx.ID(i).getText());
            if (symbolTable.lookup(currentID) == null) {
                System.out.println("Note: Decorator @" + currentID + " used at line " + ctx.getStart().getLine());
            }
        }

        List<Expression> args = null;
        if (ctx.argList() != null) {
            ArgumentList argList = (ArgumentList) visit(ctx.argList());
            args = new ArrayList<>();
            if (argList != null && argList.getArguments() != null) {
                for (Argument a : argList.getArguments()) {
                    args.add(a.getValue());
                }
            }
        }
        Decorator decorator = new Decorator(nameParts, args);
        decorator.setLineNumber(ctx.getStart().getLine());

        return decorator;
    }

    @Override
    public ASTNode visitAssignment(pythonParser.AssignmentContext ctx) {
        ASTNode targetNode = visit(ctx.target());
        ASTNode valueNode = visit(ctx.expression());

        if (targetNode instanceof Target && valueNode instanceof Expression) {
            Target target = (Target) targetNode;
            Expression value = (Expression) valueNode;

            String varName = target.getBaseName();
            int line = ctx.getStart().getLine();

            symbolTable.define(varName, "Variable", line);
            System.out.println("Symbol Table Update: Defined '" + varName + "' at line " + line);
            // ---------------------------

            Assignment assignment = new Assignment(target, value);
            assignment.setLineNumber(line);
            return assignment;
        }
        return null;
    }

    @Override
    public ASTNode visitExpressionStatement(pythonParser.ExpressionStatementContext ctx) {
        ASTNode node = visit(ctx.expression());
        if (node instanceof Expression) {
            ExpressionStatement exprStmt = new ExpressionStatement((Expression) node);
            exprStmt.setLineNumber(ctx.getStart().getLine());
            return exprStmt;
        }
        return null;
    }

    @Override
    public ASTNode visitIfStatement(pythonParser.IfStatementContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());

        Block thenBlock = (Block) visit(ctx.block());

        List<ElifPart> elifParts = new ArrayList<>();
        for (pythonParser.ElifPartContext elifCtx : ctx.elifPart()) {
            ElifPart elif = (ElifPart) visit(elifCtx);
            if (elif != null) {
                elifParts.add(elif);
            }
        }

        ElsePart elsePart = null;
        if (ctx.elsePart() != null) {
            elsePart = (ElsePart) visit(ctx.elsePart());
        }

        IfStatement ifStmt = new IfStatement(condition, thenBlock, elifParts, elsePart);
        ifStmt.setLineNumber(ctx.getStart().getLine());

        return ifStmt;
    }

    @Override
    public ASTNode visitElifPart(pythonParser.ElifPartContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());

        Block block = (Block) visit(ctx.block());

        ElifPart elifNode = new ElifPart(condition, block);
        elifNode.setLineNumber(ctx.getStart().getLine());

        return elifNode;
    }

    @Override
    public ASTNode visitElsePart(pythonParser.ElsePartContext ctx) {
        Block block = (Block) visit(ctx.block());
        ElsePart elseNode = new ElsePart(block);
        elseNode.setLineNumber(ctx.getStart().getLine());

        return elseNode;
    }

    @Override
    public ASTNode visitWhileStatement(pythonParser.WhileStatementContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());
        Block block = (Block) visit(ctx.block());

        WhileStatement whileStmt = new WhileStatement(condition, block);
        whileStmt.setLineNumber(ctx.getStart().getLine());

        return whileStmt;
    }

    @Override
    public ASTNode visitForStatement(pythonParser.ForStatementContext ctx) {
        String variable = ctx.ID().getText();
        int line = ctx.getStart().getLine();

        symbolTable.define(variable, "Loop-Iterator", line);
        System.out.println("Symbol Table: Defined Iterator '" + variable + "' at line " + line);
        // ---------------------------

        ASTNode iterNode = visit(ctx.expression());
        ASTNode blockNode = visit(ctx.block());

        Expression iterable = (iterNode instanceof Expression) ? (Expression) iterNode : null;
        Block block = (blockNode instanceof Block) ? (Block) blockNode : null;

        ForStatement forStmt = new ForStatement(variable, iterable, block);
        forStmt.setLineNumber(line);
        return forStmt;
    }

    @Override
    public ASTNode visitImportStatement(pythonParser.ImportStatementContext ctx) {
        boolean isFromImport = ctx.FROM() != null;
        boolean importAll = false;
        String module = null;
        String alias = null;
        List<String> names = new ArrayList<>();

        if (!isFromImport) {
            for (int i = 0; i < ctx.ID().size(); i++) {
                names.add(ctx.ID(i).getText());
            }
            if (ctx.AS() != null) {
                alias = ctx.ID(ctx.ID().size() - 1).getText();
                names.remove(names.size() - 1);
            }
        } else {
            StringBuilder mod = new StringBuilder();
            mod.append(ctx.ID(0).getText());
            int i = 1;
            while (i < ctx.ID().size() && ctx.DOT(i - 1) != null) {
                mod.append(".").append(ctx.ID(i).getText());
                i++;
            }
            module = mod.toString();
            if (ctx.MUL() != null) {
                importAll = true;
            } else {
                for (int j = i; j < ctx.ID().size(); j++) {
                    names.add(ctx.ID(j).getText());
                }
            }
        }

        if (alias != null) {
            symbolTable.define(alias, "Module (Alias)", ctx.getStart().getLine());
        } else if (module != null) {
            symbolTable.define(module, "Module", ctx.getStart().getLine());
        }

        ImportStatement importStmt = new ImportStatement(isFromImport, module, names, alias, importAll);
        importStmt.setLineNumber(ctx.getStart().getLine());
        return importStmt;
    }

    @Override
    public ASTNode visitFunctionDef(pythonParser.FunctionDefContext ctx) {
        String funcName = ctx.ID().getText();
        int line = ctx.getStart().getLine();

        symbolTable.define(funcName, "Function", line);

        FunctionDef func = new FunctionDef(funcName);
        func.setLineNumber(line);

        // ✅ هون الإضافة — زيارة كل decorator وإضافته للشجرة
        if (ctx.decorator() != null) {
            for (pythonParser.DecoratorContext decCtx : ctx.decorator()) {
                ASTNode decNode = visit(decCtx);
                if (decNode instanceof Decorator) {
                    func.addDecorator((Decorator) decNode);
                    System.out.println("Found decorator: " + ((Decorator) decNode).getExtraInfo()
                            + " for function '" + funcName + "'");
                }
            }
        }

        // باقي الكود كما هو
        symbolTable = new PythonSymbolTable(symbolTable);
        System.out.println(">>> Entering New Scope for function: " + funcName);

        if (ctx.parameters() != null) {
            ASTNode paramsNode = visit(ctx.parameters());
            if (paramsNode instanceof ParameterList) {
                func.setParameters((ParameterList) paramsNode);
            }
        }

        ASTNode bodyNode = visit(ctx.block());
        if (bodyNode instanceof Block) {
            func.setBody((Block) bodyNode);
        }

        symbolTable.printScope("Function: " + funcName);
        symbolTable = symbolTable.getParent();
        System.out.println("<<< Returning to Parent Scope.");

        return func;
    }

    @Override
    public ASTNode visitParameters(pythonParser.ParametersContext ctx) {
        ParameterList params = new ParameterList();
        params.setLineNumber(ctx.getStart().getLine());

        for (int i = 0; i < ctx.ID().size(); i++) {
            String paramName = ctx.ID(i).getText();
            int paramLine = ctx.ID(i).getSymbol().getLine();

            symbolTable.define(paramName, "Parameter", paramLine);

            Parameter p = new Parameter(paramName);
            p.setLineNumber(paramLine);
            params.addParameter(p);
        }
        return params;
    }

    @Override
    public ASTNode visitArgument(pythonParser.ArgumentContext ctx) {
        ASTNode node = visit(ctx.expression());
        Expression value = (node instanceof Expression) ? (Expression) node : null;

        Argument argument;
        if (ctx.ID() != null) {
            argument = new Argument(ctx.ID().getText(), value);
        } else {
            argument = new Argument(value);
        }
        argument.setLineNumber(ctx.getStart().getLine());
        return argument;
    }

    @Override
    public ASTNode visitArgList(pythonParser.ArgListContext ctx) {
        ArgumentList argList = new ArgumentList();
        argList.setLineNumber(ctx.getStart().getLine());

        for (pythonParser.ArgumentContext argCtx : ctx.argument()) {
            ASTNode node = visit(argCtx);
            if (node instanceof Argument) {
                argList.addArgument((Argument) node);
            }
        }
        return argList;
    }

    @Override
    public ASTNode visitTarget(pythonParser.TargetContext ctx) {
        String baseName = ctx.ID(0).getText();
        List<TargetAccess> accesses = new ArrayList<>();

        for (int i = 1; i < ctx.ID().size(); i++) {
            MemberTarAccess member = new MemberTarAccess(ctx.ID(i).getText());
            member.setLineNumber(ctx.ID(i).getSymbol().getLine());
            accesses.add(member);
        }

        for (pythonParser.ExpressionContext exprCtx : ctx.expression()) {
            ASTNode node = visit(exprCtx);
            if (node instanceof Expression) {
                IndexAccess indexAccess = new IndexAccess((Expression) node);
                indexAccess.setLineNumber(exprCtx.getStart().getLine());
                accesses.add(indexAccess);
            }
        }

        Target target = new Target(baseName, accesses);
        target.setLineNumber(ctx.getStart().getLine());
        return target;
    }

    @Override
    public ASTNode visitExpression(pythonParser.ExpressionContext ctx) {
        if (ctx.IF() == null) {
            return visit(ctx.logic_expr(0));
        }

        ASTNode thenNode = visit(ctx.logic_expr(0));
        ASTNode condNode = visit(ctx.logic_expr(1));
        ASTNode elseNode = visit(ctx.expression());

        Expression thenExpr = (thenNode instanceof Expression) ? (Expression) thenNode : null;
        Expression condition = (condNode instanceof Expression) ? (Expression) condNode : null;
        Expression elseExpr = (elseNode instanceof Expression) ? (Expression) elseNode : null;

        ConditionalExpression condExpr = new ConditionalExpression(condition, thenExpr, elseExpr);
        condExpr.setLineNumber(ctx.getStart().getLine());
        return condExpr;
    }

    @Override
    public ASTNode visitAtom(pythonParser.AtomContext ctx) {
        Expression result = null;

        if (ctx.NUMBER() != null) {
            result = new NumberLiteral(Double.parseDouble(ctx.NUMBER().getText()));
        } else if (ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            text = text.substring(1, text.length() - 1);
            result = new StringLiteral(text);
        } else if (ctx.TRUE() != null) {
            result = new BooleanLiteral(true);
        } else if (ctx.FALSE() != null) {
            result = new BooleanLiteral(false);
        } else if (ctx.NONE() != null) {
            result = new NoneLiteral();
        } else if (ctx.ID() != null) {
            String varName = ctx.ID().getText();
            int line = ctx.getStart().getLine();

            PythonSymbolTable.Symbol symbol = symbolTable.lookup(varName);
            if (symbol == null) {
                if (!comprehensionVars.contains(varName)) {
                    System.err.println(
                            "Semantic Warning: Variable '" + varName +
                                    "' at line " + line + " is used but not defined.");
                } else {
                    System.out
                            .println("Symbol Lookup: Found '" + varName + "' (Comprehension-Var) used at line " + line);
                }
            } else {
                System.out.println("Symbol Lookup: Found '" + varName + "' (" + symbol.type + ") used at line " + line);
            }

            result = new Identifier(varName);
        } else if (ctx.listLiteral() != null) {
            return visit(ctx.listLiteral());
        } else if (ctx.dictLiteral() != null) {
            return visit(ctx.dictLiteral());
        } else if (ctx.testList_comp() != null) {
            return visit(ctx.testList_comp());
        }

        if (result != null) {
            result.setLineNumber(ctx.getStart().getLine());
        }
        return result;
    }

    @Override
    public ASTNode visitLogic_expr(pythonParser.Logic_exprContext ctx) {
        ASTNode leftNode = visit(ctx.comparison(0));
        Expression left = (leftNode instanceof Expression) ? (Expression) leftNode : null;

        for (int i = 1; i < ctx.comparison().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            ASTNode rightNode = visit(ctx.comparison(i));
            Expression right = (rightNode instanceof Expression) ? (Expression) rightNode : null;

            GenericBinaryExpression logicExpr = new GenericBinaryExpression(left, operator, right);
            logicExpr.setLineNumber(ctx.getStart().getLine());
            left = logicExpr;
        }
        return left;
    }

    @Override
    public ASTNode visitComparison(pythonParser.ComparisonContext ctx) {
        ASTNode leftNode = visit(ctx.arith_expr(0));
        Expression left = (leftNode instanceof Expression) ? (Expression) leftNode : null;

        for (int i = 1; i < ctx.arith_expr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ASTNode rightNode = visit(ctx.arith_expr(i));
            Expression right = (rightNode instanceof Expression) ? (Expression) rightNode : null;

            GenericBinaryExpression compExpr = new GenericBinaryExpression(left, op, right);
            compExpr.setLineNumber(ctx.getStart().getLine());
            left = compExpr;
        }
        return left;
    }

    @Override
    public ASTNode visitListLiteral(pythonParser.ListLiteralContext ctx) {
        if (ctx.testList_comp() == null) {
            ListExpression emptyList = new ListExpression(new ArrayList<>());
            emptyList.setLineNumber(ctx.getStart().getLine());
            return emptyList;
        }
        return visit(ctx.testList_comp());
    }

    @Override
    public ASTNode visitTestList_comp(pythonParser.TestList_compContext ctx) {

        // حالة الـ comprehension: [x for x in items] أو [x for x in items if cond]
        if (ctx.comp_for() != null) {
            pythonParser.Comp_forContext cf = ctx.comp_for();

            // إنشاء scope جديد للـ comprehension
            symbolTable = new PythonSymbolTable(symbolTable);

            // تعريف متغير الـ loop
            String loopVar = cf.target().getText();
            symbolTable.define(loopVar, "Comprehension-Var", cf.getStart().getLine());
            comprehensionVars.add(loopVar);

            // بناء العناصر
            Expression element  = (Expression) visit(ctx.expression(0));
            Expression iterable = (Expression) visit(cf.expression());

            // هل عنده condition؟
            Expression condition = null;
            if (cf.comp_iter() != null && cf.comp_iter().IF() != null) {
                condition = (Expression) visit(cf.comp_iter().expression());
            }

            // ✅ في كلا الحالتين نبني ListExpression
            ListExpression listComp = new ListExpression(element, loopVar, iterable, condition);
            listComp.setLineNumber(ctx.getStart().getLine());

            // نظّف الـ scope والـ comprehension var
            comprehensionVars.remove(loopVar);
            symbolTable = symbolTable.getParent();

            return listComp;
        }

        // حالة القائمة العادية: [1, 2, 3] أو [x, y]
        List<Expression> elements = new ArrayList<>();
        for (pythonParser.ExpressionContext exprCtx : ctx.expression()) {
            ASTNode node = visit(exprCtx);
            if (node instanceof Expression) {
                elements.add((Expression) node);
            }
        }

        ListExpression list = new ListExpression(elements);
        list.setLineNumber(ctx.getStart().getLine());
        return list;
    }

    @Override
    public ASTNode visitDictLiteral(pythonParser.DictLiteralContext ctx) {
        DictExpression dict = new DictExpression();
        dict.setLineNumber(ctx.getStart().getLine());

        for (pythonParser.DictEntryContext entryCtx : ctx.dictEntry()) {
            ASTNode keyNode = visit(entryCtx.expression(0));
            ASTNode valNode = visit(entryCtx.expression(1));

            if (keyNode instanceof Expression && valNode instanceof Expression) {
                dict.addEntry((Expression) keyNode, (Expression) valNode, entryCtx.getStart().getLine());            }
        }
        return dict;
    }

    @Override
    public ASTNode visitFactor(pythonParser.FactorContext ctx) {
        ASTNode atomNode = visit(ctx.atom());
        Expression expr = (atomNode instanceof Expression) ? (Expression) atomNode : null;

        for (pythonParser.TrailerContext tr : ctx.trailer()) {
            if (tr.LP() != null) {
                List<Expression> args = new ArrayList<>();
                if (tr.argList() != null) {
                    for (pythonParser.ArgumentContext argCtx : tr.argList().argument()) {
                        ASTNode argNode = visit(argCtx);
                        if (argNode instanceof Argument) {
                            args.add(((Argument) argNode).getValue());
                        }
                    }
                }
                FunctionCall call = new FunctionCall(expr, args);
                call.setLineNumber(tr.getStart().getLine());
                expr = call;
            } else if (tr.LC() != null) {
                ASTNode idxNode = visit(tr.expression());
                if (idxNode instanceof Expression) {
                    IndexExpression indexExpr = new IndexExpression(expr, (Expression) idxNode);
                    indexExpr.setLineNumber(tr.getStart().getLine());
                    expr = indexExpr;
                }
            } else if (tr.DOT() != null) {
                String name = tr.ID().getText();
                MemberAccess member = new MemberAccess(expr, name);
                member.setLineNumber(tr.getStart().getLine());
                expr = member;
            }
        }
        return expr;
    }

    @Override
    public ASTNode visitArith_expr(pythonParser.Arith_exprContext ctx) {
        ASTNode leftNode = visit(ctx.term(0));
        Expression left = (leftNode instanceof Expression) ? (Expression) leftNode : null;

        for (int i = 1; i < ctx.term().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ASTNode rightNode = visit(ctx.term(i));
            Expression right = (rightNode instanceof Expression) ? (Expression) rightNode : null;

            GenericBinaryExpression arithExpr = new GenericBinaryExpression(left, op, right);
            arithExpr.setLineNumber(ctx.getStart().getLine());
            left = arithExpr;
        }
        return left;
    }

    @Override
    public ASTNode visitTerm(pythonParser.TermContext ctx) {
        ASTNode leftNode = visit(ctx.factor(0));
        Expression left = (leftNode instanceof Expression) ? (Expression) leftNode : null;

        for (int i = 1; i < ctx.factor().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();

            ASTNode rightNode = visit(ctx.factor(i));
            Expression right = (rightNode instanceof Expression) ? (Expression) rightNode : null;

            GenericBinaryExpression termExpr = new GenericBinaryExpression(left, op, right);
            termExpr.setLineNumber(ctx.getStart().getLine());

            left = termExpr;
        }
        return left;
    }

}
