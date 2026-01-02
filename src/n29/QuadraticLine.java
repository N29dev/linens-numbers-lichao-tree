package n29;

public class QuadraticLine extends LineBase {
    private long a;
    private long b;
    private long c;

    public QuadraticLine(int id, String name, int cost, long a, long b, long c) {
        super(id, name, cost);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public long f(long x) {
        return a * x * x + b * x + c;
    }

    @Override
    public String getType() {
        return "Quadratic";
    }

    @Override
    public String getFormula() {
        return "y = " + a + "*x^2 + " + b + "*x + " + c;
    }
}
