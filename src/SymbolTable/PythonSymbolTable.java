package SymbolTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PythonSymbolTable {

    private Map<String, Symbol> symbols = new HashMap<>();
    private PythonSymbolTable parent;

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

    private static final Set<String> BUILT_INS = Set.of(
            "__name__", "print", "len", "next", "max",
            "min", "sum", "range", "int", "str",
            "float", "bool", "list", "dict", "type"
    );

    private static final Set<String> KNOWN_MODULES = Set.of(
            "os", "flask"
    );

    private static final Set<String> FLASK_OBJECTS = Set.of(
            "Flask", "render_template", "request", "redirect", "url_for"
    );

    public PythonSymbolTable() {
        this.parent = null;
        preloadBuiltins();
    }

    public PythonSymbolTable(PythonSymbolTable parent) {
        this.parent = parent;
    }

    private void preloadBuiltins() {
        for (String b : BUILT_INS) {
            symbols.put(b, new Symbol(b, "Builtin", 0));
        }
        for (String m : KNOWN_MODULES) {
            symbols.put(m, new Symbol(m, "Module", 0));
        }
        for (String f : FLASK_OBJECTS) {
            symbols.put(f, new Symbol(f, "Imported", 0));
        }
    }

    public void define(String name, String type, int line) {
        symbols.put(name, new Symbol(name, type, line));
    }

    public Symbol lookup(String name) {
        if (symbols.containsKey(name)) {
            return symbols.get(name);
        }
        if (parent != null) {
            return parent.lookup(name);
        }
        return null;
    }

    public PythonSymbolTable getParent() {
        return parent;
    }

    public void printScope(String scopeName) {
        System.out.println("\n--- Scope: " + scopeName + " ---");
        if (symbols.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (String name : symbols.keySet()) {
                System.out.println(name + " : " + symbols.get(name));
            }
        }
        System.out.println("------------------------\n");
    }
}
