package AST.Web;

public class FilterCall extends AST.Web.Expression {

    private AST.Web.Expression base;
    private FilterName filter;

    public FilterCall(AST.Web.Expression base, FilterName filter , int line){
        super(line);
        this.base = base;
        this.filter = filter;
    }
    @Override
    public String toString(){
        return base + "|" + filter;
    }
}
