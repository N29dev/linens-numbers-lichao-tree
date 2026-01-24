package model;

import java.util.ArrayList;

public class Orders implements InfoPrintable {
    private final Customer customer;
    private final ArrayList<LineBase> orderLines;
    private long happiness;
    private long price;
    private final int id;

    public Orders(Customer customer, int id) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
        if (id <= 0) throw new IllegalArgumentException("Invalid order id: " + id);

        this.customer = customer;
        this.id = id;
        this.orderLines = new ArrayList<>();
        this.happiness = 0;
        this.price = 0;
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public long getHappiness() { return happiness; }
    public long getPrice() { return price; }

    public String addLine(LineBase line) {
        if (line == null) return "Failed: null line.";

        orderLines.add(line);
        price += line.getCost();

        long best = Math.min(line.f(customer.getL()), line.f(customer.getR()));
        happiness += best;

        return "OK";
    }

    @Override
    public String getShortInfo() {
        return "Order id=" + id +
                " | customer=" + customer.getName() +
                " | price=" + price +
                " | happiness=" + happiness;
    }

    @Override
    public String getInfo() {
        StringBuilder res = new StringBuilder();
        res.append("Order id: ").append(id).append("\n\n");

        res.append("Customer info <<<<<<<<\n");
        res.append(customer.getInfo()).append("\n\n");

        res.append("Order lines <<<<<<<<\n");
        int i = 0;
        for (LineBase line : orderLines) {
            i++;
            res.append("Line #").append(i).append("\n");
            res.append(line.getInfo()).append("\n");

            if (line instanceof LinearLine) {
                res.append("(instanceof demo) This is LinearLine\n");
            } else if (line instanceof QuadraticLine) {
                res.append("(instanceof demo) This is QuadraticLine\n");
            }
            res.append("\n");
        }

        res.append("Total price: ").append(price).append("\n");
        res.append("Total happiness: ").append(happiness).append("\n");
        return res.toString();
    }
}
