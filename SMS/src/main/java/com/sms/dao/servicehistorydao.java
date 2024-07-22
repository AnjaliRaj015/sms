package main.java.com.sms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.java.com.sms.db.database;
import main.java.com.sms.model.serviceHistory;

public class servicehistorydao {


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
