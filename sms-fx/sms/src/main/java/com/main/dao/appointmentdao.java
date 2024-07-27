package com.main.dao;

import com.main.db.database;
import com.main.model.appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class appointmentdao {

    public static List<appointment> getAllAppointments() {
        Connection connection = database.connect();
        String query = "SELECT * FROM appointments";
        List<appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointment appointment = new appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setServiceId(rs.getInt("service_id"));
                appointment.setCustomerId(rs.getInt("customer_id"));
                appointment.setStaffId(rs.getInt("staff_id"));
                appointment.setDate(rs.getDate("date"));
                appointment.setTime(rs.getString("time"));
                appointment.setStatus(rs.getString("status"));

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    // Add method to add a new appointment
    public void addAppointment(appointment app) {
        String sql = "INSERT INTO appointments (service_id, customer_id, staff_id, date, time, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, app.getServiceId());
            pstmt.setInt(2, app.getCustomerId());
            pstmt.setInt(3, app.getStaffId());
            pstmt.setDate(4, new java.sql.Date(app.getDate().getTime()));
            pstmt.setString(5, app.getTime());
            pstmt.setString(6, app.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Add method to update appointment
    public void updateAppointment(appointment app) {
        String sql = "UPDATE appointments SET status = ?, date = ?, reschedule_count = ? WHERE id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app.getStatus());
            pstmt.setDate(2, new java.sql.Date(app.getDate().getTime()));
            pstmt.setInt(3, app.getRescheduleCount());
            pstmt.setInt(4, app.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getServiceNameById(int serviceId) {
        String serviceName = null;
        String query = "SELECT name FROM services WHERE id = ?";
        
        try (Connection conn = database.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, serviceId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                serviceName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceName;
    }
    public appointment getAppointmentById(int appointmentId) {
        appointment appointment = null;
        String query = "SELECT * FROM appointments WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                appointment = new appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setServiceId(rs.getInt("service_id"));
                appointment.setCustomerId(rs.getInt("customer_id"));
                appointment.setStaffId(rs.getInt("staff_id"));
                appointment.setDate(rs.getDate("date"));
                appointment.setTime(rs.getString("time"));
                appointment.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }
    public List<appointment> getAppointmentsByStaffId(int staffId) {
        Connection connection = database.connect();
        String query = "SELECT * FROM appointments WHERE staff_id = ?";
        List<appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointment appointment = new appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setServiceId(rs.getInt("service_id"));
                appointment.setCustomerId(rs.getInt("customer_id"));
                appointment.setStaffId(rs.getInt("staff_id"));
                appointment.setDate(rs.getDate("date"));
                appointment.setTime(rs.getString("time"));
                appointment.setStatus(rs.getString("status"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public static List<appointment> getAppointmentsByCustomerId(int customerId) {
        Connection connection = database.connect();
        String query = "SELECT a.*, s.full_name, s.phone FROM appointments a JOIN staffs s ON a.staff_id = s.id WHERE a.customer_id = ?";
        List<appointment> appointments = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointment appointment = new appointment();
                appointment.setId(rs.getInt("a.id"));
                appointment.setServiceId(rs.getInt("a.service_id"));
                appointment.setCustomerId(rs.getInt("a.customer_id"));
                appointment.setStaffId(rs.getInt("a.staff_id"));
                appointment.setDate(rs.getDate("a.date"));
                appointment.setTime(rs.getString("a.time"));
                appointment.setStatus(rs.getString("a.status"));
                appointment.setStaffName(rs.getString("s.full_name"));
                appointment.setStaffPhone(rs.getString("s.phone"));
                
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    public void assignEmployeeToAppointment(int appointmentId, int employeeId) {
        String sql = "UPDATE appointments SET staff_id = ? WHERE id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, appointmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
