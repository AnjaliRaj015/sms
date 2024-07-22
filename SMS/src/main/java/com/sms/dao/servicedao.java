package main.java.com.sms.dao;

import main.java.com.sms.db.database;
import main.java.com.sms.model.service;

import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class servicedao {
    public static List<service> getAllServices() {
        Connection connection = database.connect();
        String query = "SELECT * FROM services";
        List<service> services = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                service service = new service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setEstimatedDuration(rs.getInt("estimated_duration"));
                service.setCost(rs.getDouble("cost"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
        
    }

    public static void addService(service service) {
        String sql = "INSERT INTO services (name, description, cost, estimated_duration) VALUES (?, ?, ?, ?)";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, service.getName());
            pstmt.setString(2, service.getDescription());
            pstmt.setDouble(3, service.getCost());
            pstmt.setInt(4, service.getEstimatedDuration());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<service> getServiceByName(String serviceName) {
        Connection connection = database.connect();
        String sql = "SELECT * FROM services WHERE name = ?";
        List<service> services = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, serviceName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                service service = new service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setCost(rs.getDouble("cost"));
                return services;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    public service getServiceById(int serviceId) {
        service service = null;
        String query = "SELECT * FROM services WHERE id = ?";
        
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, serviceId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                service = new service();
                service.setId(resultSet.getInt("id"));
                service.setName(resultSet.getString("name"));
                service.setDescription(resultSet.getString("description"));
                service.setCost(resultSet.getDouble("cost"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return service;
    }
    
}
