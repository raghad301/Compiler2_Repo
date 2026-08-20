package Semantic;

import AST.Web.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSemanticAnalyzer {

    private final List<SemanticError> errors = new ArrayList<>();
    private final LinkedList<Set<String>> scopeStack = new LinkedList<>();
    private final LinkedList<Map<String, Object>> valueScopeStack = new LinkedList<>();
    private final Set<String> knownLoopVariables = new HashSet<>();
    private Set<String> pythonContextVars = new HashSet<>();
    private Map<String, Object> pythonContextValues = new HashMap<>();

    private static final Object UNKNOWN_VALUE = new Object();

    private static final Set<String> JINJA_BUILTINS = Set.of(
            "true", "false", "none", "loop", "url_for", "request", "session",
            "g", "config", "range", "len", "str", "int", "float", "list", "dict",
            "get_flashed_messages", "super", "self"
    );

    private static final Set<String> JINJA_KEYWORDS = Set.of(
            "and", "or", "not", "in", "is", "if", "else"
    );

    private static final Pattern IDENTIFIER_PATTERN =
            Pattern.compile("[A-Za-z_][A-Za-z0-9_]*");
    private static final Pattern PROPERTY_CHAIN_PATTERN = Pattern.compile(
            "([A-Za-z_][A-Za-z0-9_]*)(?:\\s*\\.\\s*([A-Za-z_][A-Za-z0-9_]*))+"
    );

    public WebSemanticAnalyzer() {
        scopeStack.addFirst(new HashSet<>());
        valueScopeStack.addFirst(new HashMap<>());
    }

    public void setPythonContext(Set<String> contextVars) {
        if (contextVars != null) {
            this.pythonContextVars = new HashSet<>(contextVars);
            this.pythonContextValues = new HashMap<>();
            for (String variable : contextVars) {
                this.pythonContextValues.put(variable, UNKNOWN_VALUE);
            }
        }
    }

    public void setPythonContext(Map<String, Object> context) {
        this.pythonContextVars = new HashSet<>();
        this.pythonContextValues = new HashMap<>();
        if (context == null) return;

        this.pythonContextVars.addAll(context.keySet());
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            this.pythonContextValues.put(
                    entry.getKey(),
                    entry.getValue() == null ? UNKNOWN_VALUE : entry.getValue());
        }
    }

    public void analyze(HtmlDocument doc) {
        if (doc == null) return;

        errors.clear();
        scopeStack.clear();
        scopeStack.addFirst(new HashSet<>());
        valueScopeStack.clear();
        valueScopeStack.addFirst(new HashMap<>());
        knownLoopVariables.clear();

        visitNode(doc);
    }

    private void visitNode(HtmlNode node) {
        if (node == null) return;

        if (node instanceof HtmlDocument) {
            for (HtmlNode child : ((HtmlDocument) node).getChildren()) {
                visitNode(child);
            }
        }
        else if (node instanceof HtmlElement) {
            HtmlElement elem = (HtmlElement) node;
            for (HtmlAttribute attr : elem.getAttributes()) {
                visitNode(attr);
            }
            for (HtmlNode child : elem.getChildren()) {
                visitNode(child);
            }
        }

        else if (node instanceof HtmlSelfClosingElement) {
            HtmlSelfClosingElement element =
                    (HtmlSelfClosingElement) node;

            for (HtmlAttribute attribute
                    : element.getAttributes()) {
                visitNode(attribute);
            }
        }

        else if (node instanceof HtmlAttribute) {
            HtmlAttribute attribute =
                    (HtmlAttribute) node;

            for (AttributeValuePart part
                    : attribute.getParts()) {

                if (part
                        instanceof AttributeJinjaExpression) {

                    AttributeJinjaExpression
                            jinjaPart =
                            (AttributeJinjaExpression) part;

                    if (jinjaPart.getExpression()
                            != null) {
                        visitNode(
                                jinjaPart.getExpression()
                        );
                    }
                }
            }
        }

        else if (node instanceof HtmlSetStatement) {
            HtmlSetStatement setStatement =
                    (HtmlSetStatement) node;

            checkVariableReference(
                    setStatement.getExpression(),
                    setStatement.getLine()
            );

            if (setStatement.getVarName() != null) {
                String variable = setStatement.getVarName().trim();
                if (scopeStack.peekFirst().contains(variable)) {
                    errors.add(new SemanticError(
                            "Duplicate Set Error: Variable '" + variable
                                    + "' is already defined in this Jinja scope.",
                            setStatement.getLine()));
                } else {
                    scopeStack.peekFirst().add(variable);
                    valueScopeStack.peekFirst().put(
                            variable,
                            inferExpressionValue(setStatement.getExpression()));
                }
            }
        }

        else if (node instanceof HtmlForBlock) {
            HtmlForBlock forBlock = (HtmlForBlock) node;

            String rawLoopVar = forBlock.getLoopVar() != null ? forBlock.getLoopVar().trim() : "";
            String rawIterable = forBlock.getIterable() != null ? forBlock.getIterable().trim() : "";

            String loopVar = "";
            String iterable = rawIterable;

            if (rawLoopVar.contains(" in ")) {
                String[] parts = rawLoopVar.split("\\s+in\\s+");
                loopVar = parts[0].trim();
                if (iterable.isEmpty() && parts.length > 1) {
                    iterable = parts[1].trim();
                }
            } else {
                loopVar = rawLoopVar;
            }

            if (!iterable.isEmpty()) {
                checkVariableReference(iterable, forBlock.getLine());
                Object iterableValue = resolveSimpleExpression(iterable);
                if (iterableValue != UNKNOWN_VALUE && !isIterableValue(iterableValue)) {
                    errors.add(new SemanticError(
                            "Iterable Type Error: Expression '" + iterable
                                    + "' is not a list or iterable collection.",
                            forBlock.getLine()));
                }
            }

            enterScope();
            if (!loopVar.isEmpty()) {
                Object loopValue = firstIterableValue(resolveSimpleExpression(iterable));
                String[] variables = loopVar.split(",");
                for (String v : variables) {
                    String variable = v.trim();
                    scopeStack.peekFirst().add(variable);
                    valueScopeStack.peekFirst().put(variable, loopValue);
                    knownLoopVariables.add(variable);
                }
            }

            if (forBlock.getBody() != null) {
                for (HtmlNode child : forBlock.getBody()) {
                    visitNode(child);
                }
            }
            exitScope();
        }

        else if (node instanceof HtmlIfBlock) {
            HtmlIfBlock ifBlock =
                    (HtmlIfBlock) node;

            checkVariableReference(
                    ifBlock.getCondition(),
                    ifBlock.getLine()
            );

            enterScope();

            for (HtmlNode child
                    : ifBlock.getThenBranch()) {
                visitNode(child);
            }

            exitScope();

            for (HtmlElifBranch elifBranch
                    : ifBlock.getElifBranches()) {

                checkVariableReference(
                        elifBranch.getCondition(),
                        elifBranch.getLine()
                );

                enterScope();

                for (HtmlNode child
                        : elifBranch.getBody()) {
                    visitNode(child);
                }

                exitScope();
            }

            if (!ifBlock.getElseBranch().isEmpty()) {
                enterScope();

                for (HtmlNode child
                        : ifBlock.getElseBranch()) {
                    visitNode(child);
                }

                exitScope();
            }
        }

        else if (node instanceof HtmlExpressionBlock) {
            HtmlExpressionBlock exprBlock = (HtmlExpressionBlock) node;
            checkVariableReference(exprBlock.getExpression(), exprBlock.getLine());
        }
    }

    private void checkVariableReference(String rawExpression, int line) {
        if (rawExpression == null || rawExpression.isBlank()) {
            return;
        }

        String expression = maskStringLiterals(rawExpression.trim());
        Matcher matcher = IDENTIFIER_PATTERN.matcher(expression);
        Set<String> checkedVariables = new HashSet<>();

        while (matcher.find()) {
            String identifier = matcher.group();
            String lowerIdentifier = identifier.toLowerCase(Locale.ROOT);

            if (JINJA_KEYWORDS.contains(lowerIdentifier)
                    || JINJA_BUILTINS.contains(lowerIdentifier)) {
                continue;
            }

            char previous = previousNonWhitespace(
                    expression,
                    matcher.start() - 1
            );

            if (previous == '.' || previous == '|') {
                continue;
            }

            int nextIndex = nextNonWhitespaceIndex(
                    expression,
                    matcher.end()
            );

            if (nextIndex >= 0
                    && expression.charAt(nextIndex) == '='
                    && (nextIndex + 1 >= expression.length()
                    || expression.charAt(nextIndex + 1) != '=')) {
                continue;
            }

            if (!checkedVariables.add(identifier)) {
                continue;
            }

            if (!isDeclared(identifier)) {
                String message = knownLoopVariables.contains(identifier)
                        ? "Loop Scope Error: Loop variable '" + identifier
                                + "' is used outside its loop scope."
                        : "Undefined Variable Error: Variable '" + identifier
                                + "' is not defined in Jinja scope or Python context.";
                errors.add(new SemanticError(message, line));
            }
        }

        validatePropertyChains(expression, line);
    }

    private String maskStringLiterals(String expression) {
        StringBuilder result = new StringBuilder(expression);
        char quote = 0;
        boolean escaped = false;

        for (int i = 0; i < result.length(); i++) {
            char current = result.charAt(i);

            if (quote != 0) {
                if (escaped) {
                    escaped = false;
                } else if (current == '\\') {
                    escaped = true;
                } else if (current == quote) {
                    quote = 0;
                }

                result.setCharAt(i, ' ');
            } else if (current == '\'' || current == '"') {
                quote = current;
                result.setCharAt(i, ' ');
            }
        }

        return result.toString();
    }

    private char previousNonWhitespace(String text, int index) {
        for (int i = index; i >= 0; i--) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return text.charAt(i);
            }
        }

        return '\0';
    }

    private int nextNonWhitespaceIndex(String text, int index) {
        for (int i = index; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return i;
            }
        }

        return -1;
    }

    private boolean isDeclared(String varName) {
        if (pythonContextVars.contains(varName)) return true;
        for (Set<String> scope : scopeStack) {
            if (scope.contains(varName)) return true;
        }
        return false;
    }

    private void validatePropertyChains(String expression, int line) {
        Matcher matcher = PROPERTY_CHAIN_PATTERN.matcher(expression);
        Set<String> checkedChains = new HashSet<>();

        while (matcher.find()) {
            String chain = matcher.group().replaceAll("\\s+", "");
            if (!checkedChains.add(chain)) continue;

            String[] parts = chain.split("\\.");
            if (JINJA_BUILTINS.contains(parts[0].toLowerCase(Locale.ROOT))) continue;

            Object value = resolveValue(parts[0]);
            if (value == UNKNOWN_VALUE) continue;

            for (int index = 1; index < parts.length; index++) {
                if (value instanceof Map<?, ?>) {
                    Map<?, ?> map = (Map<?, ?>) value;
                    if (!map.containsKey(parts[index])) {
                        errors.add(new SemanticError(
                                "Unknown Property Error: Property '" + parts[index]
                                        + "' does not exist on variable '" + parts[0] + "'.",
                                line));
                        break;
                    }
                    Object next = map.get(parts[index]);
                    value = next == null ? UNKNOWN_VALUE : next;
                } else if (value != null) {
                    errors.add(new SemanticError(
                            "Unknown Property Error: Value '" + parts[index - 1]
                                    + "' has no property '" + parts[index] + "'.",
                            line));
                    break;
                }
            }
        }
    }

    private Object inferExpressionValue(String expression) {
        if (expression == null) return UNKNOWN_VALUE;
        String value = expression.trim();
        if ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        if (value.matches("[-+]?\\d+(?:\\.\\d+)?")) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ignored) {
                return UNKNOWN_VALUE;
            }
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        return resolveSimpleExpression(value);
    }

    private Object resolveSimpleExpression(String expression) {
        if (expression == null) return UNKNOWN_VALUE;
        String value = expression.trim();
        if (IDENTIFIER_PATTERN.matcher(value).matches()) {
            return resolveValue(value);
        }
        return UNKNOWN_VALUE;
    }

    private Object resolveValue(String variable) {
        for (Map<String, Object> scope : valueScopeStack) {
            if (scope.containsKey(variable)) return scope.get(variable);
        }
        return pythonContextValues.getOrDefault(variable, UNKNOWN_VALUE);
    }

    private boolean isIterableValue(Object value) {
        return value instanceof Collection<?>
                || value instanceof Map<?, ?>
                || (value != null && value.getClass().isArray());
    }

    private Object firstIterableValue(Object value) {
        if (value == UNKNOWN_VALUE) return UNKNOWN_VALUE;
        if (value instanceof List<?>) {
            List<?> list = (List<?>) value;
            return list.isEmpty() || list.get(0) == null ? UNKNOWN_VALUE : list.get(0);
        }
        if (value instanceof Collection<?>) {
            Iterator<?> iterator = ((Collection<?>) value).iterator();
            if (iterator.hasNext()) {
                Object first = iterator.next();
                return first == null ? UNKNOWN_VALUE : first;
            }
        }
        return UNKNOWN_VALUE;
    }

    private void enterScope() {
        scopeStack.addFirst(new HashSet<>());
        valueScopeStack.addFirst(new HashMap<>());
    }

    private void exitScope() {
        if (scopeStack.size() > 1) scopeStack.removeFirst();
        if (valueScopeStack.size() > 1) valueScopeStack.removeFirst();
    }

    public boolean hasErrors() { return !errors.isEmpty(); }
    public List<SemanticError> getErrors() { return errors; }

    public void printResults() {
        System.out.println("\n=== Web Semantic Analysis Results ===");
        if (errors.isEmpty()) {
            System.out.println("✓ No semantic errors found in Web template.");
        } else {
            System.out.println("✗ Found " + errors.size() + " web semantic error(s):");
            for (SemanticError e : errors) e.report();
        }
    }
}
