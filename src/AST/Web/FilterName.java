package AST.Web;

public class FilterName {
    private String name;

    public FilterName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
