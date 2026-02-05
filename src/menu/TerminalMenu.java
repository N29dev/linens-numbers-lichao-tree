package menu;

import database.CustomerDAO;
import exception.InvalidInputException;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalMenu implements Menu {

    private final Scanner cin;
    private final CustomerDAO customerDAO = new CustomerDAO();

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
            "7. UPDATE CUSTOMER",
            "8. DELETE CUSTOMER",
            "9. SEARCH & FILTER",
            "10. SEARCH BY NAME",
            "11. HIGH-PAID CUSTOMERS (Money >= X)",
            "12. GET CUSTOMER INFO BY ID",
            "13. GET LINE INFO BY ID",
            "14. GET ORDER INFO BY ID"
    };

    public TerminalMenu(Scanner cin) {
        if (cin == null) throw new IllegalArgumentException("Scanner cannot be null");
        this.cin = cin;
        customerDAO.createTable();
        // Initialize nextCustomerId based on existing data
        List<Customer> existingCustomers = customerDAO.getAllCustomers();
        for (Customer c : existingCustomers) {
            if (c.getId() >= nextCustomerId) {
                nextCustomerId = c.getId() + 1;
            }
        }
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
                try {
                    System.out.println(updateCustomerInteractive());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (t == 8) {
                try {
                    System.out.println(deleteCustomerInteractive());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (t == 9) {
                System.out.println(searchAndFilterInteractive());
            } else if (t == 10) {
                System.out.println(searchByNameInteractive());
            } else if (t == 11) {
                System.out.println(searchByMinMoneyInteractive());
            } else if (t == 12) {
                int id = readInt("Customer id: ");
                System.out.println(getCustomerInfoById(id));
            } else if (t == 13) {
                int id = readInt("Line id: ");
                System.out.println(getLineInfoById(id));
            } else if (t == 14) {
                int id = readInt("Order id: ");
                System.out.println(getOrdersInfoById(id));
            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    private String addCustomerInteractive(){
        System.out.println("=== Add Customer ===");
        System.out.print("Customer name: ");
        String name = cin.nextLine().trim();
        int L=readInt("Customer L:");
        int R=readInt("Customer R:");
        long money=readLong("Customer money:");
        Customer x=new Customer(name,L,R,money,nextCustomerId);
        nextCustomerId++;
        customerDAO.insertCustomer(x);
        return "OK: Customer created. Customer id = " + x.getId();
    }

    private String addLineInteractive() {
        System.out.println("=== Add Line ===");
        System.out.print("Line name: ");
        String name = cin.nextLine().trim();
        int type=readInt("Line type (1 - linear, 2 - quadratic):");
        if(type==1){
            long k=readLong("Line k:");
            long b=readLong("Line b:");
            int cost=readInt("Line cost:");
            LineBase x=new LinearLine(nextLineId,name,cost,k,b);
            nextLineId++;
            lines.add(x);
            return "Ok: Line created. Line id = " + x.getId();
        }
        else if(type==2){
            long a=readLong("Line a:");
            long b=readLong("Line b:");
            long c=readLong("Line c:");
            int cost=readInt("Line cost:");
            LineBase x=new QuadraticLine(nextLineId,name,cost,a,b,c);
            nextLineId++;
            lines.add(x);
            return "Ok: Line created. Line id = " + x.getId();
        }
        else {
            return "Error: invalid line type.";
        }
    }

    private String addOrderInteractive() {
        System.out.println("=== Add Order ===");
        List<Customer> customers = customerDAO.getAllCustomers();
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
        customerDAO.updateCustomer(customer);
        orders.add(ord);
        nextOrderId++;
        return "OK: Order created. Order id = " + ord.getId();
    }

    private String listCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers.isEmpty()) return "No customers.";
        StringBuilder sb = new StringBuilder("=== Customers ===\n");
        for (Customer c : customers) sb.append(c.getShortInfo()).append('\n');
        return sb.toString();
    }

    private String updateCustomerInteractive() {
        int id = readInt("Enter Customer ID to update: ");
        Customer c = customerDAO.getCustomerById(id);
        if (c == null) return "Customer not found.";

        System.out.println("Current info: " + c.getShortInfo());
        System.out.println("Press Enter to keep current value, or type new value.");

        System.out.print("New name [" + c.getName() + "]: ");
        String name = cin.nextLine().trim();
        if (!name.isEmpty()) c.setName(name);

        String moneyStr = readOptionalString("New balance [" + c.getMoney() + "]: ");
        if (!moneyStr.isEmpty()) {
            try {
                c.setMoney(Long.parseLong(moneyStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, keeping original.");
            }
        }

        String lStr = readOptionalString("New L [" + c.getL() + "]: ");
        if (!lStr.isEmpty()) {
            try {
                int newL = Integer.parseInt(lStr);
                c.setLR(newL, c.getR());
            } catch (Exception e) {
                System.out.println("Invalid value, keeping original. " + e.getMessage());
            }
        }

        String rStr = readOptionalString("New R [" + c.getR() + "]: ");
        if (!rStr.isEmpty()) {
            try {
                int newR = Integer.parseInt(rStr);
                c.setLR(c.getL(), newR);
            } catch (Exception e) {
                System.out.println("Invalid value, keeping original. " + e.getMessage());
            }
        }

        if (customerDAO.updateCustomer(c)) {
            return "Customer updated successfully!";
        } else {
            return "Update failed.";
        }
    }

    private String deleteCustomerInteractive() {
        int id = readInt("Enter Customer ID to delete: ");
        Customer c = customerDAO.getCustomerById(id);
        if (c == null) return "Customer not found.";

        System.out.println("SURE TO DELETE THIS?");
        System.out.println(c.getInfo());
        System.out.print("Type 'yes' to confirm: ");
        String confirm = cin.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            if (customerDAO.deleteCustomer(id)) {
                return "Customer deleted successfully!";
            } else {
                return "Delete failed.";
            }
        } else {
            return "Deletion canceled.";
        }
    }

    private String searchAndFilterInteractive() {
        System.out.println("=== Search & Filter ===");
        System.out.println("1. Search by name (ILIKE)");
        System.out.println("2. Search by money range (BETWEEN)");
        System.out.println("3. High-paid customers (>= X)");
        int choice = readInt("Choice: ");

        if (choice == 1) {
            return searchByNameInteractive();
        } else if (choice == 2) {
            long min = readLong("Min money: ");
            long max = readLong("Max money: ");
            List<Customer> results = customerDAO.searchByMoneyRange(min, max);
            return formatCustomerList(results, "Search Results (Money Range)");
        } else if (choice == 3) {
            return searchByMinMoneyInteractive();
        } else {
            return "Invalid choice.";
        }
    }

    private String searchByNameInteractive() {
        System.out.print("Enter name (or partial name): ");
        String query = cin.nextLine().trim();
        List<Customer> results = customerDAO.searchByName(query);
        return formatCustomerList(results, "Search Results (Name)");
    }

    private String searchByMinMoneyInteractive() {
        long min = readLong("Enter min money: ");
        List<Customer> results = customerDAO.searchByMinMoney(min);
        return formatCustomerList(results, "High-Paid Customers");
    }

    private String formatCustomerList(List<Customer> list, String title) {
        if (list.isEmpty()) return "No matches found.";
        StringBuilder res = new StringBuilder("=== " + title + " ===\n");
        for (Customer c : list) {
            res.append(c.getShortInfo()).append("\n");
        }
        return res.toString();
    }

    private String readOptionalString(String prompt) {
        System.out.print(prompt);
        return cin.nextLine().trim();
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
        return customerDAO.getCustomerById(id);
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
