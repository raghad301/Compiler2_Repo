package Semantic;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;
import AST.parser_pkg.*;
import SymbolTable.PythonSymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzer {

    private final List<SemanticError> errors = new ArrayList<>();
    private PythonSymbolTable currentScope;
    private final Map<String, Integer> functionParamCount = new HashMap<>();

    // عشان نتتبع هل نحن جوا function أو لا (للـ Scope Error)
    private boolean insideFunction = false;

    private final Map<String, Set<String>> functionLocalVars = new HashMap<>();
    private String currentFunctionName = null;

    // Flask variables المطلوبة لما يكون في Flask app
    private static final Set<String> FLASK_REQUIRED = Set.of(
            "Flask", "render_template", "request", "redirect", "url_for"
    );
    private boolean hasFlaskImport = false;
    private boolean hasAppDefined = false;

    //  نقطة البداية 

    public void analyze(Program program) {
        currentScope = new PythonSymbolTable();
        firstPass(program);

        for (Statement stmt : program.getStatements()) {
            analyzeStatement(stmt);
        }

        // CHECK 5: Missing Flask Variables
        checkFlaskVariables(program);

        printResults();
    }

    //  المرحلة الأولى: تسجيل الدوال 

    private void firstPass(Program program) {
        for (Statement stmt : program.getStatements()) {
            if (stmt instanceof FunctionDef) {
                FunctionDef func = (FunctionDef) stmt;
                int paramCount = func.parameters != null
                        ? func.parameters.getParameters().size() : 0;
                functionParamCount.put(func.name, paramCount);
                currentScope.define(func.name, "Function", func.getLineNumber());
            }
            // نتحقق من Flask import بالمرحلة الأولى
            if (stmt instanceof ImportStatement) {
                ImportStatement imp = (ImportStatement) stmt;
                if ("flask".equalsIgnoreCase(imp.getModule())) {
                    hasFlaskImport = true;
                }
                if (imp.getNames() != null && imp.getNames().contains("Flask")) {
                    hasFlaskImport = true;
                }
            }
        }
    }

    //  تحليل الـ Statements

    private void analyzeStatement(Statement stmt) {
        if (stmt instanceof Assignment) {
            analyzeAssignment((Assignment) stmt);
        } else if (stmt instanceof FunctionDef) {
            analyzeFunctionDef((FunctionDef) stmt);
        } else if (stmt instanceof IfStatement) {
            analyzeIfStatement((IfStatement) stmt);
        } else if (stmt instanceof ForStatement) {
            analyzeForStatement((ForStatement) stmt);
        } else if (stmt instanceof WhileStatement) {
            analyzeWhileStatement((WhileStatement) stmt);
        } else if (stmt instanceof ReturnStatement) {
            analyzeReturnStatement((ReturnStatement) stmt);
        } else if (stmt instanceof ExpressionStatement) {
            analyzeExpression(((ExpressionStatement) stmt).getExpression());
        } else if (stmt instanceof ImportStatement) {
            analyzeImport((ImportStatement) stmt);
        }
    }

    //  Assignment 

    private void analyzeAssignment(Assignment stmt) {
        String valueType = analyzeExpression(stmt.getValue());
        Target target = stmt.getTarget();
        if (target != null) {
            String varName = target.toString();
            currentScope.define(varName, valueType, stmt.getLineNumber());

            // نتتبع المتغيرات المحلية جوا الـ function
            if (insideFunction && currentFunctionName != null) {
                functionLocalVars.get(currentFunctionName).add(varName);
            }

            // نتحقق إذا app اتعرّف
            if ("app".equals(varName)) {
                hasAppDefined = true;
            }
        }
    }

    //  FunctionDef 

    private void analyzeFunctionDef(FunctionDef stmt) {
        PythonSymbolTable funcScope = new PythonSymbolTable(currentScope);
        PythonSymbolTable savedScope = currentScope;
        currentScope = funcScope;

        boolean savedInsideFunction = insideFunction;
        String savedFunctionName = currentFunctionName;

        insideFunction = true;
        currentFunctionName = stmt.name;
        functionLocalVars.put(stmt.name, new HashSet<>());

        if (stmt.parameters != null) {
            for (Parameter p : stmt.parameters.getParameters()) {
                currentScope.define(p.name, "Parameter", p.getLineNumber());
                functionLocalVars.get(stmt.name).add(p.name);
            }
        }

        if (stmt.body != null) {
            analyzeBlock(stmt.body);
        }

        currentScope = savedScope;
        insideFunction = savedInsideFunction;
        currentFunctionName = savedFunctionName;
        // ✅ ما نمسح functionLocalVars — تضل محفوظة للفحص لاحقاً
    }

    //  IfStatement 

    private void analyzeIfStatement(IfStatement stmt) {
        analyzeExpression(stmt.getCondition());
        if (stmt.getThenBlock() != null) analyzeBlock(stmt.getThenBlock());

        if (stmt.getElifParts() != null) {
            for (ElifPart elif : stmt.getElifParts()) {
                analyzeExpression(elif.getCondition());
                if (elif.getBlock() != null) analyzeBlock(elif.getBlock());
            }
        }

        if (stmt.getElsePart() != null && stmt.getElsePart().getBlock() != null) {
            analyzeBlock(stmt.getElsePart().getBlock());
        }
    }

    //  ForStatement 

    private void analyzeForStatement(ForStatement stmt) {
        if (stmt.getIterator() != null) {
            currentScope.define(stmt.getIterator(), "Variable", stmt.getLineNumber());
            if (insideFunction && currentFunctionName != null) {
                functionLocalVars.get(currentFunctionName).add(stmt.getIterator()); // ✅
            }
            analyzeExpression(stmt.getIterable());

            // CHECK: Scope Error — نعمل scope جديد للـ for body
            PythonSymbolTable forScope = new PythonSymbolTable(currentScope);
            PythonSymbolTable savedScope = currentScope;
            currentScope = forScope;

            if (stmt.getBody() != null) analyzeBlock(stmt.getBody());

            currentScope = savedScope;
        }
    }
    //  WhileStatement 

    private void analyzeWhileStatement(WhileStatement stmt) {
        analyzeExpression(stmt.getCondition());

        // CHECK: Scope Error — نعمل scope جديد للـ while body
        PythonSymbolTable whileScope = new PythonSymbolTable(currentScope);
        PythonSymbolTable savedScope = currentScope;
        currentScope = whileScope;

        if (stmt.getBody() != null) analyzeBlock(stmt.getBody());

        currentScope = savedScope;
    }

    //  ReturnStatement 

    private void analyzeReturnStatement(ReturnStatement stmt) {
        // CHECK: Scope Error — return برا function
        if (!insideFunction) {
            errors.add(new SemanticError(
                    "Scope Error: 'return' statement used outside of a function",
                    stmt.getLineNumber()
            ));
        }
        if (stmt.getValue() != null) {
            analyzeExpression(stmt.getValue());
        }
    }

    //  ImportStatement 

    private void analyzeImport(ImportStatement stmt) {
        if (stmt.getModule() != null) {
            currentScope.define(stmt.getModule(), "Module", stmt.getLineNumber());
        }
        // نسجّل الـ names المستوردة كمان
        if (stmt.getNames() != null) {
            for (String name : stmt.getNames()) {
                currentScope.define(name, "Imported", stmt.getLineNumber());
            }
        }
        if (stmt.isFromImport() && "flask".equalsIgnoreCase(stmt.getModule())) {
            hasFlaskImport = true;
        }
    }

    //  Block 

    private void analyzeBlock(Block block) {
        for (Statement stmt : block.getStatements()) {
            analyzeStatement(stmt);
        }
    }

    //  تحليل الـ Expressions 

    private String analyzeExpression(Expression expr) {
        if (expr == null) return "Unknown";

        if (expr instanceof NumberLiteral)  return "Number";
        if (expr instanceof StringLiteral)  return "String";
        if (expr instanceof BooleanLiteral) return "Boolean";
        if (expr instanceof NoneLiteral)    return "None";

        if (expr instanceof Identifier) {
            return analyzeIdentifier((Identifier) expr);
        }
        if (expr instanceof BinaryExpression) {
            return analyzeBinaryExpression((BinaryExpression) expr);
        }
        if (expr instanceof FunctionCall) {
            return analyzeFunctionCall((FunctionCall) expr);
        }
        if (expr instanceof ListExpression) {
            for (Expression elem : ((ListExpression) expr).getElements()) {
                analyzeExpression(elem);
            }
            return "List";
        }
        if (expr instanceof DictExpression) {
            for (DictEntry entry : ((DictExpression) expr).getEntries()) {
                analyzeExpression(entry.getKey());
                analyzeExpression(entry.getValue());
            }
            return "Dict";
        }

        if (expr instanceof MemberAccess) {
            analyzeExpression(((MemberAccess) expr).getObject());
            return "Unknown";
        }
        if (expr instanceof IndexExpression) {
            IndexExpression ie = (IndexExpression) expr;
            analyzeExpression(ie.getObject());
            analyzeExpression(ie.getIndex());
            return "Unknown";
        }
        if (expr instanceof UnaryExpression) {
            return analyzeExpression(((UnaryExpression) expr).getOperand());
        }
        if (expr instanceof ConditionalExpression) {
            ConditionalExpression ce = (ConditionalExpression) expr;
            analyzeExpression(ce.getCondition());
            analyzeExpression(ce.getThenExpr());
            analyzeExpression(ce.getElseExpr());
            return "Unknown";
        }

        return "Unknown";
    }

    //  CHECK 1: Undefined Variable 

    private String analyzeIdentifier(Identifier expr) {
        String name = expr.getName();
        PythonSymbolTable.Symbol symbol = currentScope.lookup(name);

        if (symbol == null) {
            errors.add(new SemanticError(
                    "Undefined Variable: '" + name + "' is used before being declared",
                    expr.getLineNumber()
            ));
            return "Unknown";
        }

        // CHECK 3: Scope Error — متغير محلي مستخدم برا الـ function
        if (!insideFunction) {
            for (Map.Entry<String, Set<String>> entry : functionLocalVars.entrySet()) {
                if (entry.getValue().contains(name)) {
                    errors.add(new SemanticError(
                            "Scope Error: '" + name + "' is local to function '"
                                    + entry.getKey() + "' and cannot be used outside it",
                            expr.getLineNumber()
                    ));
                    break;
                }
            }
        }

        return symbol.type;
    }

    //  CHECK 2: Type Mismatch 

    private String analyzeBinaryExpression(BinaryExpression expr) {
        String leftType  = analyzeExpression(expr.getLeft());
        String rightType = analyzeExpression(expr.getRight());
        String op        = expr.getOperator();

        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
            if (!leftType.equals("Unknown") && !rightType.equals("Unknown")) {
                if ((leftType.equals("String") && rightType.equals("Number"))
                        || (leftType.equals("Number") && rightType.equals("String"))
                        || (leftType.equals("Boolean") && rightType.equals("Number"))
                        || (leftType.equals("Number") && rightType.equals("Boolean"))) {
                    errors.add(new SemanticError(
                            "Type Mismatch: cannot apply '" + op +
                                    "' between '" + leftType + "' and '" + rightType + "'",
                            expr.getLineNumber()
                    ));
                    return "Unknown";
                }
            }
            if (leftType.equals("String") && rightType.equals("String") && op.equals("+")) {
                return "String";
            }
            return "Number";
        }

        if (op.equals("==") || op.equals("!=") ||
                op.equals("<")  || op.equals(">")  ||
                op.equals("<=") || op.equals(">=")) {
            return "Boolean";
        }

        return "Unknown";
    }

    //  CHECK 3 & 4: Undeclared Function + Wrong Param Count 

    private String analyzeFunctionCall(FunctionCall expr) {
        int argCount = 0;
        for (Expression arg : expr.getArguments()) {
            analyzeExpression(arg);
            argCount++;
        }

        String funcName = null;
        if (expr.getCallee() instanceof Identifier) {
            funcName = ((Identifier) expr.getCallee()).getName();
        } else {
            analyzeExpression(expr.getCallee());
            return "Unknown";
        }

        PythonSymbolTable.Symbol symbol = currentScope.lookup(funcName);
        if (symbol == null) {
            errors.add(new SemanticError(
                    "Undefined Function: '" + funcName + "' is called but never declared",
                    expr.getLineNumber()
            ));
            return "Unknown";
        }

        if (functionParamCount.containsKey(funcName)) {
            int expectedCount = functionParamCount.get(funcName);
            if (argCount != expectedCount) {
                errors.add(new SemanticError(
                        "Type Error: function '" + funcName + "' expects " +
                                expectedCount + " argument(s) but got " + argCount,
                        expr.getLineNumber()
                ));
            }
        }

        return "Unknown";
    }

    //  CHECK 5: Missing Flask Variables 

    private void checkFlaskVariables(Program program) {
        if (!hasFlaskImport) return; // مش Flask project

        // نتحقق إنو app اتعرّف
        if (!hasAppDefined) {
            errors.add(new SemanticError(
                    "Missing Flask Variable: 'app = Flask(__name__)' is required but not found",
                    1
            ));
        }

        // نتحقق إنو الـ Flask functions المستوردة موجودة بالـ symbol table
        for (String flaskVar : FLASK_REQUIRED) {
            if (currentScope.lookup(flaskVar) == null) {
                errors.add(new SemanticError(
                        "Missing Flask Variable: '" + flaskVar +
                                "' is used but not imported from flask",
                        1
                ));
            }
        }
    }

    //  النتيجة 

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<SemanticError> getErrors() {
        return errors;
    }

    public void printResults() {
        System.out.println("\n=== Semantic Analysis Results ===");
        if (errors.isEmpty()) {
            System.out.println("✓ No semantic errors found.");
        } else {
            System.out.println("✗ Found " + errors.size() + " semantic error(s):\n");
            for (SemanticError e : errors) {
                e.report();
            }
        }
        System.out.println("===\n");
    }
}