package main.java.com.sms.dao;

import main.java.com.sms.db.database;
import main.java.com.sms.model.staff;
import main.java.com.sms.model.user;
import main.java.com.sms.model.customer;

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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addCustomer(customer customer) {
        String sql = "INSERT INTO users (username, email, phone, password, role, address) VALUES (?, ?, ?, ?, 'customer', ?)";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getUsername());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getPassword());
            pstmt.setString(5, customer.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = "SELECT * FROM users WHERE role = 'customer'";
        try (Connection conn = database.connect(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customer customer = new customer(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                customer.setId(rs.getInt("id"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public static customer getCustomerById(int customerId) {
        customer customer = null;
        String query = "SELECT * FROM users WHERE id = ? AND role = 'customer'";
        
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                customer = new customer(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("address")
                );
                customer.setId(resultSet.getInt("id"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
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
