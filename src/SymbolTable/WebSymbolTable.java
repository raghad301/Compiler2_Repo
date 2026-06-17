package SymbolTable;


import java.util.HashMap;
import java.util.Map;

public class WebSymbolTable {

    // ✅ إضافة كلاس Symbol مثل PythonSymbolTable
    public static class Symbol {
        public String name;
        public String type;
        public int line;

        public Symbol(String name, String type, int line) {
            this.name = name;
            this.type = type;
            this.line = line;
        }

        @Override
        public String toString() {
            return "[" + type + " at line " + line + "]";
        }
    }

    private Map<String, Symbol> symbols = new HashMap<>();
    private WebSymbolTable parent;

    public WebSymbolTable() { this.parent = null; }
    public WebSymbolTable(WebSymbolTable parent) { this.parent = parent; }

    // ✅ define الحين تقبل type و line
    public void define(String name, String type, int line) {
        Symbol symbol = new Symbol(name, type, line);
        symbols.put(name, symbol);
        System.out.println("Symbol Table Update: Defined '" + name + "' [" + type + "] at line " + line);
    }

    public Symbol lookup(String name) {
        if (symbols.containsKey(name)) {
            System.out.println("Symbol Lookup: Found '" + name + "' " + symbols.get(name));
            return symbols.get(name);
        }
        if (parent != null) return parent.lookup(name);
        System.out.println("Symbol Lookup: '" + name + "' NOT FOUND");
        return null;
    }

    public WebSymbolTable getParent() { return parent; }

    public void print() {
        System.out.println("--- Current Scope Symbols ---");
        symbols.forEach((key, sym) ->
                System.out.println(key + " : " + sym));
        if (parent != null) System.out.println("Parent scope exists.");
        System.out.println("-----------------------------");
    }
}
