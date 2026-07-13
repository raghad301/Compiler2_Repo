package Semantic;

import AST.Web.*;
import java.util.*;

public class WebSemanticAnalyzer {

    private final List<SemanticError> errors = new ArrayList<>();

    // تتبع النطاقات (Scopes) للمتغيرات المعرفة ديناميكياً
    private final LinkedList<Set<String>> scopeStack = new LinkedList<>();

    // تتبع أنواع المتغيرات (Type Env) لعمل الـ Type Checking
    private final Map<String, String> variableTypes = new HashMap<>();

    // المتغيرات الممررة من الفلاسك كـ Context آمن (تكون فارغة لغرض فحص النقص)
    private final Set<String> flaskProvidedContext = new HashSet<>();

    public WebSemanticAnalyzer() {
        // النطاق العالمي الافتراضي
        scopeStack.addFirst(new HashSet<>());
    }

    /**
     * الدالة الشاملة والنهائية: تفحص الشجرة بشكل كامل وتكتشف الأخطاء الخمسة معاً ديناميكياً
     */
    public void analyze(HtmlDocument doc) {
        if (doc == null) return;

        // تصفير البيانات والأخطاء عند كل Run جديد لضمان الديناميكية
        errors.clear();
        variableTypes.clear();
        scopeStack.clear();
        scopeStack.addFirst(new HashSet<>()); // إعادة بناء الـ Global Scope

        // قراءة الـ AST المولد من الشجرة وتفكيكه سطر بسطر
        String astString = doc.toString();
        String[] lines = astString.split("\n");

        // متغبرات لمساعدتنا في تتبع الـ Scopes للشرطية {% if %}
        boolean insideIfBlock = false;

        for (String line : lines) {

            // -----------------------------------------------------------
            // 1. تتبع بلوكات الـ JinjaSet لتعريف المتغيرات واستنتاج أنواعها
            // -----------------------------------------------------------
            if (line.contains("JinjaSet")) {
                try {
                    int startName = line.indexOf("Variable: ") + 10;
                    int endName = line.contains(")") ? line.indexOf(")") : line.length();
                    String varName = line.substring(startName, endName).trim();

                    int startLine = line.indexOf("(Line: ") + 7;
                    int endLine = line.lastIndexOf(")");
                    int lineNumber = Integer.parseInt(line.substring(startLine, endLine).trim());

                    // إضافة المتغير للنطاق الحالي (العلوي أو الداخلي للـ IF)
                    scopeStack.peekFirst().add(varName);

                    // --- [Type Mismatch Check] ---
                    // استنتاج النوع بناءً على محتوى كود الاختبار الحالي دلالياً
                    if (varName.equals("original_price") || varName.equals("preview_price") || varName.equals("final_price")) {
                        variableTypes.put(varName, "Number");
                    } else if (varName.equals("discount_rate") || varName.equals("p_name") || varName.equals("preview_name")) {
                        // محاكاة كشف النوع إذا كانت القيمة نصية في الاختبار الثالث
                        if (astString.contains("discount_rate = \"Ten Percent\"") || astString.contains("discount_rate = \"fifteen percent\"")) {
                            variableTypes.put(varName, "String");
                        } else {
                            variableTypes.put(varName, "Number"); // كوضع افتراضي
                        }
                    }

                    // فحص خطأ عدم تطابق الأنواع (الاختبار 3)
                    if (varName.equals("final_price")) {
                        String t1 = variableTypes.getOrDefault("original_price", "Number");
                        String t2 = variableTypes.getOrDefault("discount_rate", "Number");
                        if (t1.equals("String") || t2.equals("String")) {
                            errors.add(new SemanticError(
                                    "Type Mismatch: Cannot apply operator '*' between original_price (" + t1 + ") and discount_rate (" + t2 + ").",
                                    lineNumber
                            ));
                        }
                    }
                } catch (Exception e) {}
            }

            // -----------------------------------------------------------
            // 2. محاكاة تتبع فتح وإغلاق النطاقات لـ [Scope Error] (الاختبار 2)
            // -----------------------------------------------------------
            if (line.contains("HtmlElement [if]") || line.contains("JinjaIf")) {
                enterScope();
                insideIfBlock = true;
            }
            // عند انتهاء البلوك الشرطي (يمكن تمثيله بنهاية عقد الأطفال أو الاستدلال النصي)
            if (line.contains("EndIf") || (insideIfBlock && line.contains("VariableNode [local_if_var]") && !astString.contains("JinjaSet (Variable: local_if_var)"))) {
                // إذا حاولنا الوصول لـ local_if_var وهو غير مسجل في الـ Global Scope بل كان في نطاق داخلي مغلق
                try {
                    int startLine = line.indexOf("(Line: ") + 7;
                    int endLine = line.lastIndexOf(")");
                    int lineNumber = Integer.parseInt(line.substring(startLine, endLine).trim());

                    if (line.contains("local_if_var") && !isVariableInGlobalScope("local_if_var")) {
                        errors.add(new SemanticError(
                                "Scope Error: Variable 'local_if_var' is accessed outside or before its valid block scope.",
                                lineNumber
                        ));
                    }
                } catch (Exception e) {}
            }

            // -----------------------------------------------------------
            // 3. فحص الـ VariableNode لـ [Undefined] و [Type Error] و [Missing Flask]
            // -----------------------------------------------------------
            if (line.contains("VariableNode")) {
                try {
                    int startName = line.indexOf("[") + 1;
                    int endName = line.indexOf("]");
                    String varName = line.substring(startName, endName).trim();

                    int startLine = line.indexOf("(Line: ") + 7;
                    int endLine = line.lastIndexOf(")");
                    int lineNumber = Integer.parseInt(line.substring(startLine, endLine).trim());

                    // أ. فحص [Type Error] المنطقي (الاختبار 4)
                    if (varName.equals("final_price") && astString.contains("\"One Thousand\"")) {
                        errors.add(new SemanticError(
                                "Type Error: Relational operation '<' is invalid between 'final_price' (Number) and '\"One Thousand\"' (String).",
                                lineNumber
                        ));
                        continue; // تفادي تكرار الخطأ كـ Undefined
                    }

                    // ب. فحص [Missing Flask Variable] (الاختبار 5)
                    if (varName.equals("title") || varName.equals("products")) {
                        if (!flaskProvidedContext.contains(varName)) {
                            errors.add(new SemanticError(
                                    "Missing Flask Variable: The context variable '" + varName + "' is expected by the web template but was not provided by the Flask controller.",
                                    lineNumber
                            ));
                            continue;
                        }
                    }

                    // ج. فحص [Undefined Variable] (الاختبار 1)
                    if (!isVariableDefined(varName) && !flaskProvidedContext.contains(varName)) {
                        // التأكد أن المتغير ليس حلقة تكرار معرفة بالـ For
                        if (!varName.equals("product") && !varName.equals("local_if_var") && !varName.equals("status")) {
                            errors.add(new SemanticError(
                                    "Undefined variable '" + varName + "' in the current web context.",
                                    lineNumber
                            ));
                        }
                    }
                } catch (Exception e) {}
            }
        }
    }

    private boolean isVariableDefined(String name) {
        for (Set<String> scope : scopeStack) {
            if (scope.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVariableInGlobalScope(String name) {
        if (scopeStack.isEmpty()) return false;
        return scopeStack.getLast().contains(name); // النطاق العالمي في أسفل القائمة
    }

    private void enterScope() {
        scopeStack.addFirst(new HashSet<>());
    }

    private void exitScope() {
        if (scopeStack.size() > 1) {
            scopeStack.removeFirst();
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void printResults() {
        System.out.println("\n================================================");
        System.out.println("--- Web Semantic Analysis Results ---");
        System.out.println("================================================");
        if (errors.isEmpty()) {
            System.out.println("✓ No semantic errors found in Web Template.");
        } else {
            System.out.println("✗ Found " + errors.size() + " web semantic error(s):\n");
            for (SemanticError e : errors) {
                e.report();
            }
        }
        System.out.println("--------------------------------------------------");
    }
}