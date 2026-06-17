package AST.Web;

public class JinjaFor extends JinjaStatement
{
    private String variable;
    private AST.Web.Expression iterable;

    public JinjaFor(String variable, Expression iterable, int line)
    {
        super(line);
        this.variable = variable;
        this.iterable = iterable;
    }
    @Override
    public String toString()
    {
        return "for" + variable+ "in "+iterable;
    }
}
