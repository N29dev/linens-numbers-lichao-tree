package n29;

public class LinearLine extends LineBase {
    private long k;
    private long b;

    public LinearLine(int id, String name, int cost, long k, long b) {
        super(id, name, cost);
        this.k = k;
        this.b = b;
    }

    @Override
    public long f(long x) {
        return k * x + b;
    }

    @Override
    public String getType() {
        return "Linear";
    }

    @Override
    public String getFormula() {
        return "y = " + k + "*x + " + b;
    }
}
