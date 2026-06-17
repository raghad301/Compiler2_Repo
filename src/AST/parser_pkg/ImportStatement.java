package AST.parser_pkg;

import AST.Statement;

import java.util.List;

public class ImportStatement extends Statement {

    private boolean isFromImport;
    private String module;
    private List<String> names;
    private String alias;
    private boolean importAll;

    public ImportStatement(boolean isFromImport,
                           String module,
                           List<String> names,
                           String alias,
                           boolean importAll) {

        this.isFromImport = isFromImport;
        this.module = module;
        this.names = names;
        this.alias = alias;
        this.importAll = importAll;
    }

    public boolean isFromImport() {
        return isFromImport;
    }

    public String getModule() {
        return module;
    }

    public List<String> getNames() {
        return names;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isImportAll() {
        return importAll;
    }

    @Override
    protected String getExtraInfo() {
        String type = isFromImport ? "From: " : "Module: ";
        return type + module + (alias != null ? " as " + alias : "");
    }

}
