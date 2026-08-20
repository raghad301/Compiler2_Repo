package Semantic;

import AST.ASTNode;
import AST.Expression;
import AST.Statement;
import AST.parser_pkg.*;
import SymbolTable.PythonSymbolTable;

import java.io.File;
import java.util.*;

public class SemanticAnalyzer {

    private final List<SemanticError> errors = new ArrayList<>();
    private PythonSymbolTable currentScope;
    private final Map<String, Integer> functionParamCount = new HashMap<>();

    private boolean insideFunction = false;
    private final Map<String, Set<String>> functionLocalVars = new HashMap<>();
    private String currentFunctionName = null;

    private boolean hasFlaskImport = false;
    private boolean hasAppDefined = false;

    public void analyze(Program program) {
        currentScope = new PythonSymbolTable();
        errors.clear();

        firstPass(program);

        for (Statement stmt : program.getStatements()) {
            analyzeStatement(stmt);
        }

        checkFlaskVariables();
        printResults();
    }

    private void firstPass(Program program) {
        Set<String> declaredFunctions = new HashSet<>();

        for (Statement stmt : program.getStatements()) {
            if (stmt instanceof FunctionDef) {
                FunctionDef func = (FunctionDef) stmt;

                // [Error 3] تكرار تعريف دالة في نفس النطاق
                if (declaredFunctions.contains(func.name)) {
                    errors.add(new SemanticError(
                            "Duplicate Function Error: Function '" + func.name + "' is already defined in this scope.",
                            func.getLineNumber()
                    ));
                } else {
                    declaredFunctions.add(func.name);
                }

                int paramCount = func.parameters != null ? func.parameters.getParameters().size() : 0;
                functionParamCount.put(func.name, paramCount);
                currentScope.define(func.name, "Function", func.getLineNumber());
            }

            if (stmt instanceof ImportStatement) {
                ImportStatement imp = (ImportStatement) stmt;
                if ("flask".equalsIgnoreCase(imp.getModule())) {
                    hasFlaskImport = true;
                }
            }
        }
    }

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

    private void analyzeAssignment(Assignment stmt) {
        String valueType = analyzeExpression(stmt.getValue());
        Target target = stmt.getTarget();
        if (target != null) {
            String varName = target.toString();
            currentScope.define(varName, valueType, stmt.getLineNumber());

            if (insideFunction && currentFunctionName != null) {
                functionLocalVars.get(currentFunctionName).add(varName);
            }

            if ("app".equals(varName)) {
                hasAppDefined = true;
            }
        }
    }

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
    }

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

    private void analyzeForStatement(ForStatement stmt) {
        if (stmt.getIterator() != null) {
            // إنشاء نطاق الحلقة أولاً
            PythonSymbolTable forScope = new PythonSymbolTable(currentScope);
            PythonSymbolTable savedScope = currentScope;
            currentScope = forScope;

            // تعريف متغير الحلقة داخل نطاق الحلقة الجديد
            currentScope.define(stmt.getIterator(), "Loop-Iterator", stmt.getLineNumber());

            // تحليل القiterable في النطاق الخارجي أو الحالي
            analyzeExpression(stmt.getIterable());

            // تحليل جسم الحلقة
            if (stmt.getBody() != null) {
                analyzeBlock(stmt.getBody());
            }

            // العودة للنطاق السابق
            currentScope = savedScope;

            // تعريف المتغير أيضاً في النطاق الحالي لضمان رؤيته في الـ Context الخارجي
            currentScope.define(stmt.getIterator(), "Variable", stmt.getLineNumber());
        }
    }

    private void analyzeWhileStatement(WhileStatement stmt) {
        analyzeExpression(stmt.getCondition());

        PythonSymbolTable whileScope = new PythonSymbolTable(currentScope);
        PythonSymbolTable savedScope = currentScope;
        currentScope = whileScope;

        if (stmt.getBody() != null) analyzeBlock(stmt.getBody());

        currentScope = savedScope;
    }

    private void analyzeReturnStatement(ReturnStatement stmt) {
        if (!insideFunction) {
            errors.add(new SemanticError("Scope Error: 'return' statement used outside function", stmt.getLineNumber()));
        }
        if (stmt.getValue() != null) {
            analyzeExpression(stmt.getValue());
        }
    }

    private void analyzeImport(ImportStatement stmt) {
        if (stmt.getModule() != null) {
            currentScope.define(stmt.getModule(), "Module", stmt.getLineNumber());
        }
        if (stmt.getNames() != null) {
            for (String name : stmt.getNames()) {
                currentScope.define(name, "Imported", stmt.getLineNumber());
            }
        }
        if (stmt.isFromImport() && "flask".equalsIgnoreCase(stmt.getModule())) {
            hasFlaskImport = true;
        }
    }

    private void analyzeBlock(Block block) {
        for (Statement stmt : block.getStatements()) {
            analyzeStatement(stmt);
        }
    }

    private String analyzeExpression(Expression expr) {
        if (expr == null) return "Unknown";

        if (expr instanceof NumberLiteral)  return "Number";
        if (expr instanceof StringLiteral)  return "String";
        if (expr instanceof BooleanLiteral) return "Boolean";
        if (expr instanceof NoneLiteral)    return "None";

        if (expr instanceof Identifier)       return analyzeIdentifier((Identifier) expr);
        if (expr instanceof BinaryExpression) return analyzeBinaryExpression((BinaryExpression) expr);
        if (expr instanceof FunctionCall)     return analyzeFunctionCall((FunctionCall) expr);

        if (expr instanceof ListExpression) {
            ListExpression list = (ListExpression) expr;

            if (list.getLoopVariable() != null) {
                // الـiterable يُفحص قبل تعريف متغير الـcomprehension
                analyzeExpression(list.getIterable());

                PythonSymbolTable savedScope = currentScope;
                currentScope = new PythonSymbolTable(savedScope);

                try {
                    currentScope.define(
                            list.getLoopVariable(),
                            "Comprehension-Var",
                            list.getLineNumber()
                    );

                    for (Expression element : list.getElements()) {
                        analyzeExpression(element);
                    }

                    analyzeExpression(list.getCondition());

                } finally {
                    currentScope = savedScope;
                }

            } else {
                for (Expression element : list.getElements()) {
                    analyzeExpression(element);
                }
            }

            return "List";
        }
        if (expr instanceof MemberAccess) {
            analyzeExpression(((MemberAccess) expr).getObject());
            return "Unknown";
        }

        if (expr instanceof IndexExpression) {
            IndexExpression indexExpression =
                    (IndexExpression) expr;

            analyzeExpression(indexExpression.getObject());
            analyzeExpression(indexExpression.getIndex());
            return "Unknown";
        }

        if (expr instanceof UnaryExpression) {
            analyzeExpression(
                    ((UnaryExpression) expr).getOperand()
            );
            return "Unknown";
        }

        if (expr instanceof ConditionalExpression) {
            ConditionalExpression conditional =
                    (ConditionalExpression) expr;

            analyzeExpression(conditional.getCondition());
            analyzeExpression(conditional.getThenExpr());
            analyzeExpression(conditional.getElseExpr());
            return "Unknown";
        }

        if (expr instanceof DictExpression) {
            DictExpression dictionary =
                    (DictExpression) expr;

            for (DictEntry entry : dictionary.getEntries()) {
                analyzeExpression(entry.getKey());
                analyzeExpression(entry.getValue());
            }

            return "Dict";
        }

        return "Unknown";
    }

    private String analyzeIdentifier(Identifier expr) {
        String name = expr.getName();

        PythonSymbolTable.Symbol symbol = currentScope.lookup(name);

        // [Error 1] متغير غير معرف
        if (symbol == null) {
            errors.add(new SemanticError("Undefined Variable: '" + name + "' is used before being declared.", expr.getLineNumber()));
            return "Unknown";
        }
        return symbol.type;
    }

    private String analyzeBinaryExpression(BinaryExpression expr) {
        String leftType  = analyzeExpression(expr.getLeft());
        String rightType = analyzeExpression(expr.getRight());
        String op        = expr.getOperator();

        // [Error 5] القسمة على صفر
        if ("/".equals(op) && expr.getRight() instanceof NumberLiteral) {
            NumberLiteral num = (NumberLiteral) expr.getRight();
            if (num.getValue() == 0) {
                errors.add(new SemanticError("Arithmetic Error: Division by zero is strictly prohibited.", expr.getLineNumber()));
            }
        }

        // [Error 4] عدم توافق الأنواع في العمليات
        if ("+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op)) {
            if (("String".equals(leftType) && "Number".equals(rightType)) ||
                    ("Number".equals(leftType) && "String".equals(rightType))) {
                errors.add(new SemanticError("Type Mismatch: Cannot perform '" + op + "' between '" + leftType + "' and '" + rightType + "'.", expr.getLineNumber()));
            }
        }
        return "Unknown";
    }

    private String analyzeFunctionCall(FunctionCall expr) {
        analyzeExpression(expr.getCallee());
        String funcName = null;
        if (expr.getCallee() instanceof Identifier) {
            funcName = ((Identifier) expr.getCallee()).getName();
        }

        // [Error 2] التحقق من وجود القالب عند استدعاء render_template
        if ("render_template".equals(funcName) && !expr.getArguments().isEmpty()) {
            Expression firstArg = expr.getArguments().get(0);
            if (firstArg instanceof StringLiteral) {
                String templateName = ((StringLiteral) firstArg).getValue();
                File tFile = new File("input/templates", templateName);

                if (!tFile.isFile()) {
                    errors.add(new SemanticError(
                            "Missing Template Error: Template file '" + templateName
                                    + "' was not found in input/templates.",
                            expr.getLineNumber()
                    ));
                }
            }
        }

        for (Expression arg : expr.getArguments()) {
            analyzeExpression(arg);
        }

        return "Unknown";
    }

    private void checkFlaskVariables() {
        if (hasFlaskImport && !hasAppDefined) {
            errors.add(new SemanticError("Missing Flask Component: 'app = Flask(__name__)' is missing.", 1));
        }
    }

    public boolean hasErrors() { return !errors.isEmpty(); }
    public List<SemanticError> getErrors() { return errors; }

    public void printResults() {
        System.out.println("\n=== Python Semantic Analysis Results ===");
        if (errors.isEmpty()) {
            System.out.println("✓ No semantic errors found in Python code.");
        } else {
            System.out.println("✗ Found " + errors.size() + " semantic error(s):");
            for (SemanticError e : errors) e.report();
        }
    }
}