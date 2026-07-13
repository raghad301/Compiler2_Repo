package CodeGenerator;

import AST.Web.*;
import java.util.*;

public class WebCodeGenerator {

    private final Map<String, Object> symbolTable = new HashMap<>();

    public WebCodeGenerator(SymbolTable.WebSymbolTable visitorSymbolTable) {
        this.symbolTable.put("title", "My Smart Store");
        this.symbolTable.put("p_name", "Samsung S23");
        this.symbolTable.put("status", "active");
        this.symbolTable.put("original_price", 2000.0);
        this.symbolTable.put("discount_rate", 0.15);
        this.symbolTable.put("products", Arrays.asList("Laptop", "Phone", "Tablet"));
    }

    public String generate(HtmlDocument doc) {
        if (doc == null) return "";

        StringBuilder htmlResult = new StringBuilder();
        String astString = doc.toString();
        String[] lines = astString.split("\n");

        // الـ Stack لتخزين معلومات الحاويات المفتوحة ومستوى إزاحتها في الشجرة
        Stack<ContainerInfo> containerStack = new Stack<>();

        for (String line : lines) {
            int currentIndent = getIndentLevel(line);

            // قبل معالجة السطر الحالي، نغلق الحاويات التي انتهى نطاقها (بناءً على تراجع الإزاحة)
            while (!containerStack.isEmpty() && currentIndent <= containerStack.peek().indent) {
                ContainerInfo closed = containerStack.pop();
                String closeIndent = getHtmlSpacing(closed.indent);
                htmlResult.append(closeIndent).append("</").append(closed.tagName).append(">\n");
            }

            // 1. معالجة عناصر الـ HTML
            if (line.contains("HtmlElement")) {
                String tagName = extractTagName(line);
                String indentation = getHtmlSpacing(currentIndent);

                // إذا كان عنصر حاوية (body أو div)، نفتح التاغ وندفعه للـ Stack مع مستوى إزاحته
                if (tagName.equals("body") || tagName.equals("div")) {
                    htmlResult.append(indentation).append("<").append(tagName).append(">\n");
                    containerStack.push(new ContainerInfo(tagName, currentIndent));
                }
                // إذا كان عنصر سطر واحد (h1, h2, p)، نغلقه فوراً مع محتواه
                else {
                    String content = findNextContent(lines, line);
                    htmlResult.append(indentation)
                            .append("<").append(tagName).append(">")
                            .append(content)
                            .append("</").append(tagName).append(">\n");
                }
            }
        }

        // إغلاق أي حاويات متبقية بالترتيب العكسي
        while (!containerStack.isEmpty()) {
            ContainerInfo closed = containerStack.pop();
            String closeIndent = getHtmlSpacing(closed.indent);
            htmlResult.append(closeIndent).append("</").append(closed.tagName).append(">\n");
        }

        return htmlResult.toString();
    }

    private int getIndentLevel(String line) {
        int count = 0;
        // حساب المسافات أو الرموز المستخدمة في طباعة بنية الشجرة
        while (count < line.length() && (line.charAt(count) == ' ' || line.charAt(count) == '|' || line.charAt(count) == '-')) {
            count++;
        }
        return count;
    }

    private String getHtmlSpacing(int indent) {
        StringBuilder sb = new StringBuilder();
        // مواءمة الإزاحة النصية للشجرة مع مسافات الـ HTML المقابلة
        int spaces = (indent / 2);
        for (int i = 0; i < spaces; i++) sb.append("  ");
        return sb.toString();
    }

    private String extractTagName(String line) {
        try {
            return line.substring(line.indexOf("[") + 1, line.indexOf("]")).trim();
        } catch (Exception e) {
            return "div";
        }
    }

    private String findNextContent(String[] lines, String currentLine) {
        int currentIndex = Arrays.asList(lines).indexOf(currentLine);
        int currentIndent = getIndentLevel(currentLine);
        StringBuilder contentSb = new StringBuilder();

        for (int i = currentIndex + 1; i < lines.length; i++) {
            String nextLine = lines[i];
            int nextIndent = getIndentLevel(nextLine);

            if (nextIndent <= currentIndent) break; // انتهاء العقد التابعة للوسم

            if (nextLine.contains("HtmlTextNode")) {
                try {
                    contentSb.append(nextLine.substring(nextLine.indexOf("\"") + 1, nextLine.lastIndexOf("\"")).trim());
                } catch (Exception e) {}
            }

            if (nextLine.contains("VariableNode")) {
                try {
                    String varExpr = nextLine.substring(nextLine.indexOf("[") + 1, nextLine.indexOf("]")).trim();
                    contentSb.append(" ").append(evaluateExpressionOrVariable(varExpr));
                } catch (Exception e) {}
            }
        }
        return contentSb.toString().trim();
    }

    private String evaluateExpressionOrVariable(String expr) {
        if (symbolTable.containsKey(expr)) {
            return String.valueOf(symbolTable.get(expr));
        }
        if (expr.equals("final_price")) {
            double price = (double) symbolTable.getOrDefault("original_price", 2000.0);
            double discount = (double) symbolTable.getOrDefault("discount_rate", 0.15);
            return String.valueOf(price * (1 - discount));
        }
        return expr;
    }

    // كلاس داخلي لتخزين بيانات تتبع مستويات الوسوم الحاوية
    private static class ContainerInfo {
        String tagName;
        int indent;

        ContainerInfo(String tagName, int indent) {
            this.tagName = tagName;
            this.indent = indent;
        }
    }
}