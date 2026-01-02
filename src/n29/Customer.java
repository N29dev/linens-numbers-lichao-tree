package n29;

public class Customer {
    private String name;
    private int l;
    private int r;
    private long money;
    private int id;

    public Customer(String name, int l, int r, long money, int id) {
        if (!setName(name)) throw new IllegalArgumentException("Invalid name");
        if (!setLR(l, r)) throw new IllegalArgumentException("Invalid range");
        if (!setMoney(money)) throw new IllegalArgumentException("Invalid money");
        if (!setId(id)) throw new IllegalArgumentException("Invalid id");
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

    public boolean setLR(int l, int r) {
        if (l > r) return false;
        this.l = l;
        this.r = r;
        return true;
    }

    public boolean setMoney(long money) {
        if (money < 0) return false;
        this.money = money;
        return true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getL() { return l; }
    public int getR() { return r; }
    public long getMoney() { return money; }

    public String getShortInfo() {
        return "Customer id=" + id + " | " + name + " | range=[" + l + "," + r + "] | money=" + money;
    }

    public String getInfo() {
        return "Customer id: " + id +
                "\nCustomer name: " + name +
                "\nFavorite range: [" + l + "," + r + "]" +
                "\nCustomer balance: " + money;
    }
}
