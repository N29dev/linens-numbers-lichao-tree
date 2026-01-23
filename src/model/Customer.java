package model;

public class Customer implements InfoPrintable {
    private String name;
    private int l;
    private int r;
    private long money;
    private int id;

    public Customer(String name, int l, int r, long money, int id) {
        setName(name);
        setLR(l, r);
        setMoney(money);
        setId(id);
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Invalid id: " + id);
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Invalid name");
        this.name = name.trim();
    }

    public void setLR(int l, int r) {
        if (l > r) throw new IllegalArgumentException("Invalid range: L > R");
        this.l = l;
        this.r = r;
    }

    public void setMoney(long money) {
        if (money < 0) throw new IllegalArgumentException("Invalid money: " + money);
        this.money = money;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getL() { return l; }
    public int getR() { return r; }
    public long getMoney() { return money; }

    @Override
    public String getShortInfo() {
        return "Customer id=" + id + " | " + name + " | range=[" + l + "," + r + "] | money=" + money;
    }

    @Override
    public String getInfo() {
        return "Customer id: " + id +
                "\nCustomer name: " + name +
                "\nFavorite range: [" + l + "," + r + "]" +
                "\nCustomer balance: " + money;
    }
}
