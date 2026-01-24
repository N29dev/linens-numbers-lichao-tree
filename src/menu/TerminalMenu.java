package menu;

import exception.InvalidInputException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TerminalMenu implements Menu {

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

    public TerminalMenu(Scanner cin) {
        if (cin == null) throw new IllegalArgumentException("Scanner cannot be null");
        this.cin = cin;
    }

    @Override
    public void displayMenu() {
        for (String option : options) {
            System.out.println(option);
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println();
            displayMenu();
            int t = readInt("Your choice: ");

            if (t == 0) {
                System.out.println("Bye!");
                return;
            } else if (t == 1) {
                try {
                    System.out.println(addCustomerInteractive());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (t == 2) {
                try {
                    System.out.println(addLineInteractive());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (t == 3) {
                try {
                    System.out.println(addOrderInteractive());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
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

    private String addCustomerInteractive() {
        System.out.println("=== Add Customer ===");
        System.out.print("Name: ");
        String name = cin.nextLine().trim();

        int l = readInt("L: ");
        int r = readInt("R: ");
        long money = readLong("Money: ");
        Customer c = new Customer(name, l, r, money, nextCustomerId);
        customers.add(c);
        nextCustomerId++;
        return "OK: Customer added. id = " + c.getId();
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

        lines.add(line);
        nextLineId++;
        return "OK: Line added. id = " + line.getId();
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

    private String listCustomers() {
        if (customers.isEmpty()) return "No customers.";
        StringBuilder sb = new StringBuilder("=== Customers ===\n");
        for (Customer c : customers) sb.append(c.getShortInfo()).append('\n');
        return sb.toString();
    }

    private String listLines() {
        if (lines.isEmpty()) return "No lines.";
        StringBuilder sb = new StringBuilder("=== Lines ===\n");
        for (LineBase l : lines) sb.append(l.getShortInfo()).append('\n');
        return sb.toString();
    }

    private String listOrders() {
        if (orders.isEmpty()) return "No orders.";
        StringBuilder sb = new StringBuilder("=== Orders ===\n");
        for (Orders o : orders) sb.append(o.getShortInfo()).append('\n');
        return sb.toString();
    }

    private String getCustomerInfoById(int id) {
        Customer c = getCustomerById(id);
        if (c == null) return "Failed to find Customer with id " + id;
        return c.getInfo();
    }

    private String getLineInfoById(int id) {
        LineBase l = getLineById(id);
        if (l == null) return "Failed to find Line with id " + id;
        return l.getInfo();
    }

    private String getOrdersInfoById(int id) {
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

    private int parseIntChecked(String s) throws InvalidInputException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid int, try again.");
        }
    }

    private long parseLongChecked(String s) throws InvalidInputException {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid long, try again.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = cin.nextLine().trim();
            try {
                return parseIntChecked(s);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = cin.nextLine().trim();
            try {
                return parseLongChecked(s);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
