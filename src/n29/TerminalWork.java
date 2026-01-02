package n29;

import java.util.ArrayList;
import java.util.Scanner;

public class TerminalWork {

    private final Scanner cin;

    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<LineBase> lines = new ArrayList<>();
    private final ArrayList<Orders> orders = new ArrayList<>();

    private int nextCustomerId = 1;
    private int nextLineId = 1;
    private int nextOrderId = 1;

    private final String[] options = {
            "WRITE CORRESPONDING NUMBER",
            "0. EXIT TERMINAL",
            "1. ADD NEW CUSTOMER",
            "2. ADD NEW LINE (Linear / Quadratic)",
            "3. ADD NEW ORDER (choose customer + lines)",
            "4. LIST OF CUSTOMERS",
            "5. LIST OF LINES",
            "6. LIST OF ORDERS",
            "7. GET CUSTOMER INFO BY ID",
            "8. GET LINE INFO BY ID",
            "9. GET ORDER INFO BY ID"
    };

    public TerminalWork(Scanner cin) {
        this.cin = cin;
    }

    public void run() {
        while (true) {
            System.out.println();
            startInfo();
            int t = readInt("Your choice: ");

            if (t == 0) {
                System.out.println("Bye!");
                return;
            } else if (t == 1) {
                System.out.println(addCustomerInteractive());
            } else if (t == 2) {
                System.out.println(addLineInteractive());
            } else if (t == 3) {
                System.out.println(addOrderInteractive());
            } else if (t == 4) {
                System.out.println(listCustomers());
            } else if (t == 5) {
                System.out.println(listLines());
            } else if (t == 6) {
                System.out.println(listOrders());
            } else if (t == 7) {
                int id = readInt("Customer id: ");
                System.out.println(getCustomerInfoById(id));
            } else if (t == 8) {
                int id = readInt("Line id: ");
                System.out.println(getLineInfoById(id));
            } else if (t == 9) {
                int id = readInt("Order id: ");
                System.out.println(getOrdersInfoById(id));
            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    private void startInfo() {
        for (String option : options) {
            System.out.println(option);
        }
    }

    private String addCustomerInteractive() {
        System.out.println("=== Add Customer ===");
        System.out.print("Name: ");
        String name = cin.nextLine().trim();

        int l = readInt("L: ");
        int r = readInt("R: ");
        long money = readLong("Money: ");

        Customer c = new Customer(name, l, r, money, nextCustomerId);
        String res = addCustomer(c);
        if (res.startsWith("OK")) nextCustomerId++;
        return res;
    }

    private String addLineInteractive() {
        System.out.println("=== Add Line ===");
        System.out.println("Type: 1 = Linear (k*x + b), 2 = Quadratic (a*x^2 + b*x + c)");
        int type = readInt("Type: ");

        System.out.print("Name: ");
        String name = cin.nextLine().trim();

        int cost = readInt("Cost: ");

        LineBase line;
        if (type == 1) {
            long k = readLong("k: ");
            long b = readLong("b: ");
            line = new LinearLine(nextLineId, name, cost, k, b);
        } else if (type == 2) {
            long a = readLong("a: ");
            long b = readLong("b: ");
            long c = readLong("c: ");
            line = new QuadraticLine(nextLineId, name, cost, a, b, c);
        } else {
            return "Failed: unknown line type.";
        }

        String res = addLine(line);
        if (res.startsWith("OK")) nextLineId++;
        return res;
    }

    private String addOrderInteractive() {
        System.out.println("=== Add Order ===");
        if (customers.isEmpty()) return "Failed: no customers. Add customer first.";
        if (lines.isEmpty()) return "Failed: no lines. Add lines first.";

        int customerId = readInt("Customer id: ");
        Customer customer = getCustomerById(customerId);
        if (customer == null) return "Failed to find customer with id " + customerId;

        Orders ord = new Orders(customer, nextOrderId);

        System.out.println("Now add lines to the order.");
        System.out.println("Write line id to add, or -1 to finish.");

        while (true) {
            int lineId = readInt("Line id (-1 to finish): ");
            if (lineId == -1) break;

            LineBase line = getLineById(lineId);
            if (line == null) {
                System.out.println("No line with id " + lineId);
                continue;
            }

            String addRes = ord.addLine(line);
            if (!addRes.startsWith("OK")) {
                System.out.println(addRes);
            } else {
                System.out.println("Added: " + line.getShortInfo());
            }
        }


        long total = ord.getPrice();
        if (customer.getMoney() < total) {
            return "Failed: customer has not enough money. Need " + total + ", has " + customer.getMoney();
        }

        customer.setMoney(customer.getMoney() - total);
        orders.add(ord);
        nextOrderId++;
        return "OK: Order created. Order id = " + ord.getId();
    }


    public String addCustomer(Customer x) {
        if (x == null) return "Failed: null customer.";
        if (x.getName() == null || x.getName().isBlank()) return "Failed: invalid name.";
        if (x.getL() > x.getR()) return "Failed: invalid range (L > R).";
        if (x.getMoney() < 0) return "Failed: invalid money.";

        customers.add(x);
        return "OK: Customer added. id = " + x.getId();
    }

    public String addLine(LineBase x) {
        if (x == null) return "Failed: null line.";
        if (x.getName() == null || x.getName().isBlank()) return "Failed: invalid name.";
        if (x.getCost() <= 0) return "Failed: invalid cost.";

        lines.add(x);
        return "OK: Line added. id = " + x.getId();
    }

    public String listCustomers() {
        if (customers.isEmpty()) return "No customers.";
        StringBuilder sb = new StringBuilder("=== Customers ===\n");
        for (Customer c : customers) {
            sb.append(c.getShortInfo()).append('\n');
        }
        return sb.toString();
    }

    public String listLines() {
        if (lines.isEmpty()) return "No lines.";
        StringBuilder sb = new StringBuilder("=== Lines ===\n");
        for (LineBase l : lines) {
            sb.append(l.getShortInfo()).append('\n');
        }
        return sb.toString();
    }

    public String listOrders() {
        if (orders.isEmpty()) return "No orders.";
        StringBuilder sb = new StringBuilder("=== Orders ===\n");
        for (Orders o : orders) {
            sb.append("Order id=").append(o.getId())
                    .append(" | customer=").append(o.getCustomer().getName())
                    .append(" | price=").append(o.getPrice())
                    .append(" | happiness=").append(o.getHappiness())
                    .append('\n');
        }
        return sb.toString();
    }

    public String getCustomerInfoById(int id) {
        Customer c = getCustomerById(id);
        if (c == null) return "Failed to find Customer with id " + id;
        return c.getInfo();
    }

    public String getLineInfoById(int id) {
        LineBase l = getLineById(id);
        if (l == null) return "Failed to find Line with id " + id;
        return l.getInfo();
    }

    public String getOrdersInfoById(int id) {
        Orders o = getOrderById(id);
        if (o == null) return "Failed to find Orders with id " + id;
        return o.getInfo();
    }

    private Customer getCustomerById(int id) {
        for (Customer c : customers) if (c.getId() == id) return c;
        return null;
    }

    private LineBase getLineById(int id) {
        for (LineBase l : lines) if (l.getId() == id) return l;
        return null;
    }

    private Orders getOrderById(int id) {
        for (Orders o : orders) if (o.getId() == id) return o;
        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = cin.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("Invalid int, try again.");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = cin.nextLine().trim();
            try {
                return Long.parseLong(s);
            } catch (Exception e) {
                System.out.println("Invalid long, try again.");
            }
        }
    }
}
