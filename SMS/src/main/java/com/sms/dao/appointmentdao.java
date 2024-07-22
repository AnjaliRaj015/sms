package main.java.com.sms.dao;

import main.java.com.sms.db.database;
import main.java.com.sms.model.appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class appointmentdao {

    public static List<appointment> getAppointmentsByCustomerId(int customerId) {
        Connection connection = database.connect();
        String query = "SELECT a.*, u.full_name, u.phone FROM appointments a JOIN users u ON a.staff_id = u.id WHERE a.customer_id = ?";
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
                
                // Set staff details
                appointment.setStaffName(rs.getString("u.full_name"));
                appointment.setStaffPhone(rs.getString("u.phone"));
                
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

    // Add method to update appointment
    public static void updateAppointment(appointment app) {
        String sql = "UPDATE appointments SET service_id = ?, customer_id = ?, staff_id = ?, date = ?, time = ? WHERE id = ?";
        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, app.getServiceId());
            pstmt.setInt(2, app.getCustomerId());
            pstmt.setInt(3, app.getStaffId());
            pstmt.setDate(4, new java.sql.Date(app.getDate().getTime()));
            pstmt.setString(5, app.getTime());
            pstmt.setInt(6, app.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add method to add a new appointment
    public static void addAppointment(appointment app) {
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
    
    
}
