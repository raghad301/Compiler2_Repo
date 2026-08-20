package CodeGenerator;

import AST.Web.*;
import Web.WebLexer;
import Web.WebParser;
import Web.WebParserBaseVisitor;
import org.antlr.v4.runtime.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** Generates static HTML from the Web/Jinja AST. */
public class WebCodeGenerator {

    private static final Set<String> VOID_TAGS = Set.of(
            "area", "base", "br", "col", "embed", "hr", "img",
            "input", "link", "meta", "source", "track", "wbr"
    );

    private static final Set<String> BLOCK_TAGS = Set.of(
            "html", "head", "body", "title", "div", "p", "form",
            "ul", "ol", "li", "h1", "h2", "h3", "h4", "h5", "h6",
            "section", "article", "header", "footer", "nav", "main",
            "table", "thead", "tbody", "tr", "td", "th"
    );

    private final Map<String, Object> baseContext;
    private final Deque<Map<String, Object>> scopes = new ArrayDeque<>();

    public WebCodeGenerator(Object symbolTable) {
        this(symbolTable instanceof Map
                ? castContext(symbolTable)
                : Collections.emptyMap());
    }

    public WebCodeGenerator(Object symbolTable, Map<String, Object> pythonContext) {
        this(pythonContext);
    }

    public WebCodeGenerator(Map<String, Object> pythonContext) {
        baseContext = new LinkedHashMap<>();
        if (pythonContext != null) baseContext.putAll(pythonContext);
        baseContext.putIfAbsent("request", Collections.emptyMap());
        baseContext.putIfAbsent("session", Collections.emptyMap());
        baseContext.putIfAbsent("g", Collections.emptyMap());
        baseContext.putIfAbsent("config", Collections.emptyMap());
        baseContext.putIfAbsent("self", Collections.emptyMap());
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> castContext(Object value) {
        return (Map<String, Object>) value;
    }

    public String generate(HtmlDocument document) {
        if (document == null) {
            throw new GenerationException("Cannot generate a null HTML document.");
        }
        scopes.clear();
        scopes.push(new LinkedHashMap<>(baseContext));

        StringBuilder output = new StringBuilder();
        for (HtmlNode child : document.getChildren()) renderNode(child, output);
        return output.toString();
    }

    private void renderNode(HtmlNode node, StringBuilder output) {
        if (node == null) return;

        if (node instanceof HtmlElement) {
            renderElement((HtmlElement) node, output);
        } else if (node instanceof HtmlSelfClosingElement) {
            renderSelfClosingElement((HtmlSelfClosingElement) node, output);
        } else if (node instanceof HtmlText) {
            output.append(((HtmlText) node).getText());
        } else if (node instanceof HtmlExpressionBlock) {
            Object value = evaluate(((HtmlExpressionBlock) node).getExpression());
            output.append(escapeHtml(formatValue(value)));
        } else if (node instanceof HtmlSetStatement) {
            HtmlSetStatement set = (HtmlSetStatement) node;
            setVariable(set.getVarName(), evaluate(set.getExpression()));
        } else if (node instanceof HtmlForBlock) {
            renderForBlock((HtmlForBlock) node, output);
        } else if (node instanceof HtmlIfBlock) {
            renderIfBlock((HtmlIfBlock) node, output);
        } else if (node instanceof StyleElement) {
            renderStyleElement((StyleElement) node, output);
        } else if (node instanceof ScriptElement) {
            renderScriptElement((ScriptElement) node, output);
        } else {
            throw new GenerationException(
                    "Unsupported Web AST node: " + node.getClass().getName());
        }
    }

    private void renderElement(HtmlElement element, StringBuilder output) {
        String tag = normalizeTagName(element.getTagName());
        output.append('<').append(tag);
        renderAttributes(element.getAttributes(), output);
        output.append('>');

        if (!VOID_TAGS.contains(tag)) {
            for (HtmlNode child : element.getChildren()) renderNode(child, output);
            output.append("</").append(tag).append('>');
        }
        appendBlockLineBreak(tag, output);
    }

    private void renderSelfClosingElement(
            HtmlSelfClosingElement element,
            StringBuilder output
    ) {
        String tag = normalizeTagName(element.getTagName());
        output.append('<').append(tag);
        renderAttributes(element.getAttributes(), output);
        output.append(VOID_TAGS.contains(tag) ? ">" : " />");
        appendBlockLineBreak(tag, output);
    }

    private void renderAttributes(List<HtmlAttribute> attributes, StringBuilder output) {
        if (attributes == null) return;

        for (HtmlAttribute attribute : attributes) {
            if (attribute == null || attribute.getName() == null
                    || attribute.getName().isBlank()) continue;

            output.append(' ').append(attribute.getName());
            if (!attribute.hasValue()) continue;

            StringBuilder value = new StringBuilder();
            for (AttributeValuePart part : attribute.getParts()) {
                if (part instanceof AttributeText) {
                    value.append(((AttributeText) part).getText());
                } else if (part instanceof AttributeJinjaExpression) {
                    HtmlExpressionBlock expression =
                            ((AttributeJinjaExpression) part).getExpression();
                    if (expression != null) {
                        value.append(formatValue(evaluate(expression.getExpression())));
                    }
                } else if (part != null) {
                    value.append(renderEmbeddedExpressions(part.toString()));
                }
            }

            output.append("=\"")
                    .append(escapeAttribute(value.toString()))
                    .append('"');
        }
    }

    private void renderForBlock(HtmlForBlock block, StringBuilder output) {
        List<Object> values = toIterationList(evaluate(block.getIterable()));

        for (int index = 0; index < values.size(); index++) {
            Map<String, Object> loopScope = new LinkedHashMap<>();
            loopScope.put(block.getLoopVar().trim(), values.get(index));

            Map<String, Object> loop = new LinkedHashMap<>();
            loop.put("index", index + 1);
            loop.put("index0", index);
            loop.put("first", index == 0);
            loop.put("last", index == values.size() - 1);
            loop.put("length", values.size());
            loopScope.put("loop", loop);

            scopes.push(loopScope);
            try {
                renderNodes(block.getBody(), output);
            } finally {
                scopes.pop();
            }
        }
    }

    private void renderIfBlock(HtmlIfBlock block, StringBuilder output) {
        if (isTruthy(evaluate(block.getCondition()))) {
            renderScopedNodes(block.getThenBranch(), output);
            return;
        }

        for (HtmlElifBranch branch : block.getElifBranches()) {
            if (isTruthy(evaluate(branch.getCondition()))) {
                renderScopedNodes(branch.getBody(), output);
                return;
            }
        }
        renderScopedNodes(block.getElseBranch(), output);
    }

    private void renderScopedNodes(List<? extends HtmlNode> nodes, StringBuilder output) {
        scopes.push(new LinkedHashMap<>());
        try {
            renderNodes(nodes, output);
        } finally {
            scopes.pop();
        }
    }

    private void renderNodes(List<? extends HtmlNode> nodes, StringBuilder output) {
        if (nodes == null) return;
        for (HtmlNode node : nodes) renderNode(node, output);
    }

    private void renderStyleElement(StyleElement style, StringBuilder output) {
        output.append("<style>");
        for (CssNode child : style.getChildren()) {
            output.append(renderEmbeddedExpressions(child.toString()));
        }
        output.append("</style>\n");
    }

    private void renderScriptElement(ScriptElement script, StringBuilder output) {
        output.append(renderEmbeddedExpressions(script.getOpeningTag()));
        if (script.getContent() != null) output.append(script.getContent());
        for (ScriptNode child : script.getChildren()) {
            if (child instanceof ScriptText) {
                output.append(((ScriptText) child).getText());
            } else {
                output.append(renderEmbeddedExpressions(child.toString()));
            }
        }
        output.append("</script>\n");
    }

    private String renderEmbeddedExpressions(String text) {
        if (text == null || text.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        int cursor = 0;
        while (cursor < text.length()) {
            int start = text.indexOf("{{", cursor);
            if (start < 0) {
                result.append(text.substring(cursor));
                break;
            }
            int end = text.indexOf("}}", start + 2);
            if (end < 0) {
                throw new GenerationException(
                        "Unclosed embedded Jinja expression during generation.");
            }
            result.append(text, cursor, start);
            result.append(formatValue(evaluate(text.substring(start + 2, end).trim())));
            cursor = end + 2;
        }
        return result.toString();
    }

    private Object evaluate(String expression) {
        if (expression == null || expression.isBlank()) return null;

        WebLexer lexer = new WebLexer(CharStreams.fromString(
                "{{ " + expression.trim() + " }}"));
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        WebParser parser = new WebParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        WebParser.JinjaExpressionContext parsed = parser.jinjaExpression();
        return new JinjaExpressionEvaluator().visit(parsed.expression());
    }

    private final class JinjaExpressionEvaluator extends WebParserBaseVisitor<Object> {

        @Override
        public Object visitLogicalOrExpr(WebParser.LogicalOrExprContext context) {
            List<WebParser.LogicalAndExpressionContext> operands =
                    context.logicalAndExpression();
            Object result = visit(operands.get(0));
            for (int i = 1; i < operands.size(); i++) {
                if (isTruthy(result)) return result;
                result = visit(operands.get(i));
            }
            return result;
        }

        @Override
        public Object visitLogicalAndExpr(WebParser.LogicalAndExprContext context) {
            List<WebParser.ComparisonExpressionContext> operands =
                    context.comparisonExpression();
            Object result = visit(operands.get(0));
            for (int i = 1; i < operands.size(); i++) {
                if (!isTruthy(result)) return result;
                result = visit(operands.get(i));
            }
            return result;
        }

        @Override
        public Object visitComparisonExpr(WebParser.ComparisonExprContext context) {
            List<WebParser.SimpleExpressionContext> operands = context.simpleExpression();
            Object left = visit(operands.get(0));
            if (operands.size() == 1) return left;

            Object right = visit(operands.get(1));
            String operator = context.getChild(1).getText();
            switch (operator) {
                case "==": return valuesEqual(left, right);
                case "!=": return !valuesEqual(left, right);
                case ">": return compareValues(left, right) > 0;
                case "<": return compareValues(left, right) < 0;
                case ">=": return compareValues(left, right) >= 0;
                case "<=": return compareValues(left, right) <= 0;
                case "in": return containsValue(right, left);
                default:
                    throw new GenerationException(
                            "Unsupported comparison operator: " + operator);
            }
        }

        @Override
        public Object visitAddSubExpr(WebParser.AddSubExprContext context) {
            List<WebParser.TermContext> operands = context.term();
            Object result = visit(operands.get(0));
            for (int i = 1; i < operands.size(); i++) {
                String operator = context.getChild(i * 2 - 1).getText();
                result = applyAddSub(result, operator, visit(operands.get(i)));
            }
            return result;
        }

        @Override
        public Object visitMulDivExpr(WebParser.MulDivExprContext context) {
            List<WebParser.FactorContext> operands = context.factor();
            Object result = visit(operands.get(0));
            for (int i = 1; i < operands.size(); i++) {
                String operator = context.getChild(i * 2 - 1).getText();
                result = applyMulDiv(result, operator, visit(operands.get(i)));
            }
            return result;
        }

        @Override
        public Object visitFactor(WebParser.FactorContext context) {
            Object result = visit(context.primary());
            for (int i = context.getChildCount() - 2; i >= 0; i--) {
                String operator = context.getChild(i).getText();
                switch (operator) {
                    case "not": result = !isTruthy(result); break;
                    case "+": result = toNumber(result); break;
                    case "-": result = -toNumber(result); break;
                    default:
                        throw new GenerationException(
                                "Unsupported unary operator: " + operator);
                }
            }
            return result;
        }

        @Override
        public Object visitNumberLiteral(WebParser.NumberLiteralContext context) {
            return Double.parseDouble(context.JINJA_NUMBER().getText());
        }

        @Override
        public Object visitStringLiteral(WebParser.StringLiteralContext context) {
            return decodeStringLiteral(context.JINJA_STRING().getText());
        }

        @Override
        public Object visitParenExpr(WebParser.ParenExprContext context) {
            return visit(context.expression());
        }

        @Override
        public Object visitFilterExpr(WebParser.FilterExprContext context) {
            return applyFilter(
                    visit(context.primary()),
                    context.JINJA_NAME().getText());
        }

        @Override
        public Object visitVariableExpr(WebParser.VariableExprContext context) {
            String rootName = context.JINJA_NAME(0).getText();

            if (context.LPAREN() != null) {
                if (!context.DOT().isEmpty() || !context.LBRACKET().isEmpty()) {
                    throw new GenerationException(
                            "Method calls on member/index expressions are not supported: "
                                    + context.getText());
                }
                return invokeFunction(rootName, evaluateArguments(context));
            }

            Object value = resolveVariable(rootName);
            int nameIndex = 1;
            for (int i = 1; i < context.getChildCount(); i++) {
                String text = context.getChild(i).getText();
                if (".".equals(text)) {
                    value = accessMember(
                            value,
                            context.JINJA_NAME(nameIndex++).getText());
                    i++;
                } else if ("[".equals(text)) {
                    value = accessIndex(value, visit(context.getChild(i + 1)));
                    i += 2;
                }
            }
            return value;
        }

        private CallArguments evaluateArguments(WebParser.VariableExprContext context) {
            CallArguments result = new CallArguments();
            if (context.callArguments() == null) return result;

            for (WebParser.CallArgumentContext argument
                    : context.callArguments().callArgument()) {
                Object value = visit(argument.expression());
                if (argument.ASSIGN() != null && argument.JINJA_NAME() != null) {
                    result.keyword.put(argument.JINJA_NAME().getText(), value);
                } else {
                    result.positional.add(value);
                }
            }
            return result;
        }
    }

    private Object invokeFunction(String functionName, CallArguments arguments) {
        switch (functionName) {
            case "url_for": return generateUrl(arguments);
            case "len":
                requirePositionalCount(functionName, arguments, 1);
                return lengthOf(arguments.positional.get(0));
            case "str":
                requirePositionalCount(functionName, arguments, 1);
                return formatValue(arguments.positional.get(0));
            case "int":
                requirePositionalCount(functionName, arguments, 1);
                return (long) toNumber(arguments.positional.get(0));
            case "float":
                requirePositionalCount(functionName, arguments, 1);
                return toNumber(arguments.positional.get(0));
            case "range": return generateRange(arguments);
            case "list":
                requirePositionalCount(functionName, arguments, 1);
                return toIterationList(arguments.positional.get(0));
            case "dict":
                if (arguments.positional.size() > 1) {
                    throw new GenerationException(
                            "dict expects at most one positional argument.");
                }
                Map<String, Object> dictionary = new LinkedHashMap<>();
                if (!arguments.positional.isEmpty()) {
                    Object source = arguments.positional.get(0);
                    if (!(source instanceof Map)) {
                        throw new GenerationException(
                                "dict positional argument must be a map.");
                    }
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) source).entrySet()) {
                        dictionary.put(String.valueOf(entry.getKey()), entry.getValue());
                    }
                }
                dictionary.putAll(arguments.keyword);
                return dictionary;
            case "get_flashed_messages":
                return Collections.emptyList();
            case "super":
                return "";
            default:
                throw new GenerationException(
                        "Unsupported Jinja function: " + functionName);
        }
    }

    private String generateUrl(CallArguments arguments) {
        if (arguments.positional.isEmpty()) {
            throw new GenerationException("url_for requires an endpoint name.");
        }

        String endpoint = formatValue(arguments.positional.get(0));
        if ("static".equals(endpoint)) {
            Object filename = arguments.keyword.get("filename");
            if (filename == null) {
                throw new GenerationException(
                        "url_for('static', ...) requires filename=.");
            }
            return formatValue(filename);
        }

        if ("delete_product".equals(endpoint)) {
            Object productId = arguments.keyword.get("product_id");
            if (productId == null) {
                throw new GenerationException(
                        "url_for('delete_product', ...) requires product_id=.");
            }
            return "index.html?delete_product="
                    + urlEncode(formatValue(productId));
        }

        StringBuilder url = new StringBuilder(endpoint).append(".html");
        boolean first = true;
        for (Map.Entry<String, Object> entry : arguments.keyword.entrySet()) {
            url.append(first ? '?' : '&');
            first = false;
            url.append(urlEncode(entry.getKey()))
                    .append('=')
                    .append(urlEncode(formatValue(entry.getValue())));
        }
        return url.toString();
    }

    private List<Object> generateRange(CallArguments arguments) {
        if (!arguments.keyword.isEmpty() || arguments.positional.isEmpty()
                || arguments.positional.size() > 3) {
            throw new GenerationException(
                    "range expects one to three positional arguments.");
        }

        int start;
        int stop;
        int step;
        if (arguments.positional.size() == 1) {
            start = 0;
            stop = (int) toNumber(arguments.positional.get(0));
            step = 1;
        } else {
            start = (int) toNumber(arguments.positional.get(0));
            stop = (int) toNumber(arguments.positional.get(1));
            step = arguments.positional.size() == 3
                    ? (int) toNumber(arguments.positional.get(2)) : 1;
        }
        if (step == 0) throw new GenerationException("range step cannot be zero.");

        List<Object> values = new ArrayList<>();
        if (step > 0) {
            for (int value = start; value < stop; value += step) values.add(value);
        } else {
            for (int value = start; value > stop; value += step) values.add(value);
        }
        return values;
    }

    private Object applyFilter(Object value, String filterName) {
        switch (filterName.toLowerCase(Locale.ROOT)) {
            case "length": return lengthOf(value);
            case "lower": return formatValue(value).toLowerCase(Locale.ROOT);
            case "upper": return formatValue(value).toUpperCase(Locale.ROOT);
            case "title": return toTitleCase(formatValue(value));
            case "string": return formatValue(value);
            case "int": return (long) toNumber(value);
            case "float": return toNumber(value);
            case "first": {
                List<Object> values = toIterationList(value);
                return values.isEmpty() ? null : values.get(0);
            }
            case "last": {
                List<Object> values = toIterationList(value);
                return values.isEmpty() ? null : values.get(values.size() - 1);
            }
            default:
                throw new GenerationException(
                        "Unsupported Jinja filter: " + filterName);
        }
    }

    private Object applyAddSub(Object left, String operator, Object right) {
        if ("+".equals(operator)
                && (left instanceof String || right instanceof String)) {
            return formatValue(left) + formatValue(right);
        }
        double leftNumber = toNumber(left);
        double rightNumber = toNumber(right);
        return "+".equals(operator)
                ? leftNumber + rightNumber : leftNumber - rightNumber;
    }

    private Object applyMulDiv(Object left, String operator, Object right) {
        double leftNumber = toNumber(left);
        double rightNumber = toNumber(right);
        switch (operator) {
            case "*": return leftNumber * rightNumber;
            case "/":
                ensureNonZero(rightNumber, operator);
                return leftNumber / rightNumber;
            case "//":
                ensureNonZero(rightNumber, operator);
                return Math.floor(leftNumber / rightNumber);
            case "%":
                ensureNonZero(rightNumber, operator);
                return leftNumber % rightNumber;
            default:
                throw new GenerationException(
                        "Unsupported arithmetic operator: " + operator);
        }
    }

    private void ensureNonZero(double number, String operator) {
        if (Double.compare(number, 0.0) == 0) {
            throw new GenerationException(
                    "Division by zero while evaluating operator " + operator + ".");
        }
    }

    private Object resolveVariable(String name) {
        if ("true".equalsIgnoreCase(name)) return true;
        if ("false".equalsIgnoreCase(name)) return false;
        if ("none".equalsIgnoreCase(name)) return null;

        for (Map<String, Object> scope : scopes) {
            if (scope.containsKey(name)) return scope.get(name);
        }
        throw new GenerationException(
                "Variable '" + name + "' is unavailable during generation.");
    }

    private void setVariable(String name, Object value) {
        if (name == null || name.isBlank()) {
            throw new GenerationException("Cannot assign an empty Jinja variable name.");
        }
        scopes.peek().put(name.trim(), value);
    }

    private Object accessMember(Object object, String memberName) {
        if (object == null) return null;
        if (object instanceof Map) return ((Map<?, ?>) object).get(memberName);
        if ("length".equals(memberName)) return lengthOf(object);

        String suffix = Character.toUpperCase(memberName.charAt(0))
                + memberName.substring(1);
        for (String methodName : List.of("get" + suffix, "is" + suffix)) {
            try {
                Method method = object.getClass().getMethod(methodName);
                return method.invoke(object);
            } catch (ReflectiveOperationException ignored) {
                // Try the next supported access form.
            }
        }
        try {
            Field field = object.getClass().getField(memberName);
            return field.get(object);
        } catch (ReflectiveOperationException ignored) {
            throw new GenerationException(
                    "Member '" + memberName + "' does not exist on "
                            + object.getClass().getSimpleName() + ".");
        }
    }

    private Object accessIndex(Object object, Object index) {
        if (object == null) return null;

        if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            Object direct = map.get(index);
            if (direct != null || map.containsKey(index)) return direct;
            return map.get(formatValue(index));
        }

        int numericIndex = (int) toNumber(index);
        if (object instanceof List) return ((List<?>) object).get(numericIndex);
        if (object.getClass().isArray()) return Array.get(object, numericIndex);
        if (object instanceof String) {
            return String.valueOf(((String) object).charAt(numericIndex));
        }
        throw new GenerationException(
                "Value of type " + object.getClass().getSimpleName()
                        + " cannot be indexed.");
    }

    private List<Object> toIterationList(Object value) {
        if (value == null) return Collections.emptyList();

        List<Object> result = new ArrayList<>();
        if (value instanceof Map) {
            result.addAll(((Map<?, ?>) value).keySet());
        } else if (value instanceof Iterable) {
            for (Object item : (Iterable<?>) value) result.add(item);
        } else if (value instanceof Iterator) {
            Iterator<?> iterator = (Iterator<?>) value;
            while (iterator.hasNext()) result.add(iterator.next());
        } else if (value.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(value); i++) {
                result.add(Array.get(value, i));
            }
        } else if (value instanceof String) {
            for (char character : ((String) value).toCharArray()) {
                result.add(String.valueOf(character));
            }
        } else {
            throw new GenerationException(
                    "Value of type " + value.getClass().getSimpleName()
                            + " is not iterable.");
        }
        return result;
    }

    private int lengthOf(Object value) {
        if (value == null) return 0;
        if (value instanceof CharSequence) return ((CharSequence) value).length();
        if (value instanceof Collection) return ((Collection<?>) value).size();
        if (value instanceof Map) return ((Map<?, ?>) value).size();
        if (value.getClass().isArray()) return Array.getLength(value);
        throw new GenerationException(
                "Value of type " + value.getClass().getSimpleName()
                        + " has no length.");
    }

    private boolean containsValue(Object container, Object sought) {
        if (container == null) return false;
        if (container instanceof Map) {
            return ((Map<?, ?>) container).containsKey(sought)
                    || ((Map<?, ?>) container).containsKey(formatValue(sought));
        }
        if (container instanceof Collection) {
            return ((Collection<?>) container).contains(sought);
        }
        if (container instanceof String) {
            return ((String) container).contains(formatValue(sought));
        }
        if (container.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(container); i++) {
                if (valuesEqual(Array.get(container, i), sought)) return true;
            }
            return false;
        }
        throw new GenerationException("Right operand of 'in' is not iterable.");
    }

    private boolean valuesEqual(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return Double.compare(
                    ((Number) left).doubleValue(),
                    ((Number) right).doubleValue()) == 0;
        }
        return Objects.equals(left, right);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private int compareValues(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            return Double.compare(
                    ((Number) left).doubleValue(),
                    ((Number) right).doubleValue());
        }
        if (left == null || right == null) {
            throw new GenerationException("Cannot order null values.");
        }
        if (left.getClass().isInstance(right) && left instanceof Comparable) {
            return ((Comparable) left).compareTo(right);
        }
        return formatValue(left).compareTo(formatValue(right));
    }

    private double toNumber(Object value) {
        if (value instanceof Number) return ((Number) value).doubleValue();
        try {
            return Double.parseDouble(formatValue(value));
        } catch (NumberFormatException exception) {
            throw new GenerationException(
                    "Value '" + formatValue(value) + "' is not numeric.");
        }
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Number) {
            return Double.compare(((Number) value).doubleValue(), 0.0) != 0;
        }
        if (value instanceof CharSequence) return ((CharSequence) value).length() > 0;
        if (value instanceof Collection) return !((Collection<?>) value).isEmpty();
        if (value instanceof Map) return !((Map<?, ?>) value).isEmpty();
        if (value.getClass().isArray()) return Array.getLength(value) > 0;
        return true;
    }

    private String formatValue(Object value) {
        if (value == null) return "";
        if (value instanceof Number) {
            double number = ((Number) value).doubleValue();
            if (Double.isFinite(number) && number == Math.rint(number)) {
                return Long.toString((long) number);
            }
        }
        return String.valueOf(value);
    }

    private String decodeStringLiteral(String literal) {
        if (literal == null || literal.length() < 2) {
            return literal == null ? "" : literal;
        }
        String body = literal.substring(1, literal.length() - 1);
        StringBuilder decoded = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < body.length(); i++) {
            char character = body.charAt(i);
            if (escaped) {
                switch (character) {
                    case 'n': decoded.append('\n'); break;
                    case 'r': decoded.append('\r'); break;
                    case 't': decoded.append('\t'); break;
                    default: decoded.append(character); break;
                }
                escaped = false;
            } else if (character == '\\') {
                escaped = true;
            } else {
                decoded.append(character);
            }
        }
        if (escaped) decoded.append('\\');
        return decoded.toString();
    }

    private String toTitleCase(String value) {
        StringBuilder result = new StringBuilder();
        boolean capitalize = true;
        for (char character : value.toCharArray()) {
            if (Character.isWhitespace(character)) {
                capitalize = true;
                result.append(character);
            } else if (capitalize) {
                result.append(Character.toTitleCase(character));
                capitalize = false;
            } else {
                result.append(Character.toLowerCase(character));
            }
        }
        return result.toString();
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private String escapeAttribute(String value) {
        return escapeHtml(value)
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private String normalizeTagName(String tagName) {
        if (tagName == null || tagName.isBlank()) {
            throw new GenerationException("HTML element has an empty tag name.");
        }
        return tagName.trim().toLowerCase(Locale.ROOT);
    }

    private void appendBlockLineBreak(String tag, StringBuilder output) {
        if (BLOCK_TAGS.contains(tag) || "br".equals(tag) || "hr".equals(tag)) {
            output.append('\n');
        }
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

    private void requirePositionalCount(
            String functionName,
            CallArguments arguments,
            int count
    ) {
        if (arguments.positional.size() != count || !arguments.keyword.isEmpty()) {
            throw new GenerationException(
                    functionName + " expects " + count + " positional argument(s).");
        }
    }

    private static final class CallArguments {
        private final List<Object> positional = new ArrayList<>();
        private final Map<String, Object> keyword = new LinkedHashMap<>();
    }

    private static final class ThrowingErrorListener extends BaseErrorListener {
        private static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer,
                Object offendingSymbol,
                int line,
                int charPositionInLine,
                String message,
                RecognitionException exception
        ) {
            throw new GenerationException(
                    "Invalid Jinja expression during generation at "
                            + line + ":" + charPositionInLine + ": " + message);
        }
    }

    public static class GenerationException extends RuntimeException {
        public GenerationException(String message) {
            super(message);
        }
    }
}
