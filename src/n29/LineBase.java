package n29;

public abstract class LineBase {
    protected int id;
    protected String name;
    protected int cost;

    public LineBase(int id, String name, int cost) {
        if (!setId(id)) throw new IllegalArgumentException("Invalid id");
        if (!setName(name)) throw new IllegalArgumentException("Invalid name");
        if (!setCost(cost)) throw new IllegalArgumentException("Invalid cost");
    }

    public boolean setId(int id) {
        if (id <= 0) return false;
        this.id = id;
        return true;
    }

    public boolean setName(String name) {
        if (name == null || name.isBlank()) return false;
        this.name = name.trim();
        return true;
    }

    public boolean setCost(int cost) {
        if (cost <= 0) return false;
        this.cost = cost;
        return true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCost() { return cost; }

    public abstract long f(long x);
    public abstract String getType();
    public abstract String getFormula();

    public String getShortInfo() {
        return "Line id=" + id + " | " + name + " | type=" + getType() + " | cost=" + cost;
    }

    public String getInfo() {
        return "Line id: " + id +
                "\nLine name: " + name +
                "\nLine type: " + getType() +
                "\nLine formula: " + getFormula() +
                "\nLine cost: " + cost;
    }
}
