package com.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class reportdao {

    private Connection connection;

    public reportdao(Connection connection) {
        this.connection = connection;
    }

    public Map<String, Double> getRevenueByServiceType() throws SQLException {
        Map<String, Double> revenueMap = new HashMap<>();
        String query = "SELECT s.name AS service_name, SUM(s.cost) AS total_revenue FROM appointments ap JOIN services s ON ap.service_id = s.id JOIN services se ON se.id = ap.service_id WHERE ap.date BETWEEN '2024-07-01' AND '2024-10-31' GROUP BY s.name;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String serviceType = resultSet.getString("service_name");
                double totalRevenue = resultSet.getDouble("total_revenue");
                revenueMap.put(serviceType, totalRevenue);
            }
        }
        return revenueMap;
    }

    public Map<String, Integer> getWorkloadDistribution() throws SQLException {
        Map<String, Integer> workloadMap = new HashMap<>();
        String query = "SELECT st.full_name AS staff_name, COUNT(a.id) AS total_appointments FROM appointments a JOIN staffs st ON a.staff_id = st.id GROUP BY st.full_name ORDER BY total_appointments DESC;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String staffName = resultSet.getString("staff_name");
                int workload = resultSet.getInt("total_appointments");
                workloadMap.put(staffName, workload);
            }
        }
        return workloadMap;
    }
}
