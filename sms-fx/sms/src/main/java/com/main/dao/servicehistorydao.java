package com.main.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.main.db.database;
import com.main.model.serviceHistory;

public class servicehistorydao {
    // add service history
    public void addServiceHistory(serviceHistory app) {
        String sql = "INSERT INTO service_history (customer_id, service_name, date, cost) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, app.getCustomerId());
            pstmt.setString(2, app.getServiceName());
            pstmt.setDate(3, new java.sql.Date(app.getDate().getTime()));
            pstmt.setDouble(4, app.getCost());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add service histroy by customer id
    public static List<serviceHistory> getServiceHistoryByCustomerId(int customerId) {
        Connection connection = database.connect();
        List<serviceHistory> histories = new ArrayList<>();
        String query = "SELECT * FROM service_history WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    serviceHistory history = new serviceHistory();
                    history.setId(resultSet.getInt("id"));
                    history.setCustomerId(resultSet.getInt("customer_id"));
                    history.setServiceName(resultSet.getString("service_name"));
                    history.setDate(resultSet.getDate("date"));
                    history.setCost(resultSet.getDouble("cost"));
                    histories.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }
}
