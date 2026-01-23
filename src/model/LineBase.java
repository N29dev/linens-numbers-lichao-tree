package model;

public abstract class LineBase implements InfoPrintable {
    protected int id;
    protected String name;
    protected int cost;

    public LineBase(int id, String name, int cost) {
        setId(id);
        setName(name);
        setCost(cost);
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Invalid id: " + id);
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Invalid name");
        this.name = name.trim();
    }

    public void setCost(int cost) {
        if (cost <= 0) throw new IllegalArgumentException("Invalid cost: " + cost);
        this.cost = cost;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCost() { return cost; }

    public abstract long f(long x);
    public abstract String getType();
    public abstract String getFormula();

    @Override
    public String getShortInfo() {
        return "Line id=" + id + " | " + name + " | type=" + getType() + " | cost=" + cost;
    }

    @Override
    public String getInfo() {
        return "Line id: " + id +
                "\nLine name: " + name +
                "\nLine type: " + getType() +
                "\nLine formula: " + getFormula() +
                "\nLine cost: " + cost;
    }
}
