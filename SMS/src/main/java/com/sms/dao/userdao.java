package main.java.com.sms.dao;

import main.java.com.sms.db.database;
import main.java.com.sms.model.customer;
import main.java.com.sms.model.staff;
import main.java.com.sms.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userdao {
    
    public user getUser(String username, String password) {
        Connection connection = database.connect();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user user = new user();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                System.out.println("User logged in with ID: " + user.getId());
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(user user) {
        String sql = "INSERT INTO users (full_name, username, password, role, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getAddress());
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            System.out.println("User added with ID: " + user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCustomer(customer customer) {
        // First, add the user
        addUser(customer);
        
        // Then, add the customer
        String sql = "INSERT INTO customers (user_id, username, password, full_name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customer.getId()); // This should be the generated user ID
            pstmt.setString(2, customer.getUsername());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getName());
            pstmt.setString(5, customer.getEmail());
            pstmt.setString(6, customer.getPhone());
            pstmt.setString(7, customer.getAddress());
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected in customers table: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getCustomerIdByUserId(int userId) {
        int customerId = -1;
        String query = "SELECT id FROM customers WHERE user_id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }
    public int getStaffIdByUserId(int userId) {
        int staffId = -1;
        String query = "SELECT id FROM staffs WHERE user_id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                staffId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffId;
    }

    public static void updateCustomer(customer customer) {
        String sql = "UPDATE users SET email = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getEmail());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<customer> getCustomers() {
        List<customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        
        try (Connection conn = database.connect(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customer customer = new customer(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("full_name"),  // Assuming full_name corresponds to 'name'
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
                customer.setId(rs.getInt("id"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public static customer getCustomerById(int customerId) {
        customer customer = null;
        String query = "SELECT * FROM customers WHERE id = ?";
        
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                customer = new customer(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("full_name"),  // Assuming full_name corresponds to 'name'
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("address")
                );
                customer.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
        
    public static List<staff> getEmployees() {
        Connection connection = database.connect();
        String query = "SELECT * FROM users WHERE role = 'staff'";
        List<staff> employees = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                staff employee = new staff();
                employee.setId(rs.getInt("id"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setRole(rs.getString("role"));
                employee.setPosition(rs.getString("position"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
