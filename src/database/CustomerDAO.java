package database;
import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS customer (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "money BIGINT NOT NULL, " +
                "l INT NOT NULL, " +
                "r INT NOT NULL" +
                ")";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            System.out.println("Table 'customer' created successfully!");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Table creation failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, money, l, r, id) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            // Set parameters (? → actual values)
            statement.setString(1, customer.getName());
            statement.setLong(2, customer.getMoney());
            statement.setInt(3, customer.getL());
            statement.setInt(4, customer.getR());
            statement.setInt(5, customer.getId());
            // Execute INSERT
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Customer inserted successfully!");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Insert failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long money = resultSet.getLong("money");
                int l = resultSet.getInt("l");
                int r = resultSet.getInt("r");
                customers.add(new Customer(name, l, r, money, id));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Select failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                long money = resultSet.getLong("money");
                int l = resultSet.getInt("l");
                int r = resultSet.getInt("r");
                return new Customer(name, l, r, money, id);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name = ?, money = ?, l = ?, r = ? WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getName());
            statement.setLong(2, customer.getMoney());
            statement.setInt(3, customer.getL());
            statement.setInt(4, customer.getR());
            statement.setInt(5, customer.getId());
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(" Update failed!");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println(" Delete failed!");
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    public List<Customer> searchByName(String nameQuery) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE name ILIKE ? ORDER BY name ASC";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + nameQuery + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long money = resultSet.getLong("money");
                int l = resultSet.getInt("l");
                int r = resultSet.getInt("r");
                customers.add(new Customer(name, l, r, money, id));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return customers;
    }

    public List<Customer> searchByMoneyRange(long min, long max) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE money BETWEEN ? AND ? ORDER BY money DESC";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, min);
            statement.setLong(2, max);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long money = resultSet.getLong("money");
                int l = resultSet.getInt("l");
                int r = resultSet.getInt("r");
                customers.add(new Customer(name, l, r, money, id));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Search by money range failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return customers;
    }

    public List<Customer> searchByMinMoney(long minMoney) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE money >= ? ORDER BY money DESC";
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, minMoney);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                long money = resultSet.getLong("money");
                int l = resultSet.getInt("l");
                int r = resultSet.getInt("r");
                customers.add(new Customer(name, l, r, money, id));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(" Search by min money failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return customers;
    }
}