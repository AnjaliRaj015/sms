package com.main.dao;

import com.main.model.QuoteRequest;
import com.main.model.appointment;
import com.main.db.database;
import com.main.model.customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuoteRequestDAO {
    private Connection connection;

    public void addQuoteRequest(QuoteRequest quoteRequest) {
        try {
            String query = "INSERT INTO quote_requests (customer_id, service_id, status, service_name, customer_name, customer_address, customer_phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quoteRequest.getCustomerId());
            statement.setInt(2, quoteRequest.getServiceId());
            statement.setString(3, quoteRequest.getStatus());
            statement.setString(4, quoteRequest.getServiceName()); // New field
            statement.setString(5, quoteRequest.getCustomerName()); // New field
            statement.setString(6, quoteRequest.getCustomerAddress()); // New field
            statement.setString(7, quoteRequest.getCustomerPhone()); // New field
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuoteRequest(QuoteRequest quoteRequest) {
        try {
            String query = "UPDATE quote_requests SET status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, quoteRequest.getStatus());
            statement.setInt(2, quoteRequest.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public QuoteRequestDAO() {
        try {
            connection = database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<QuoteRequest> getQuoteRequestsByCustomerId(int customerId) {
        List<QuoteRequest> quoteRequests = new ArrayList<>();
        try {
            String query = "SELECT * FROM quote_requests WHERE customer_id = ? and status = 'pending';";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                QuoteRequest quoteRequest = new QuoteRequest();
                quoteRequest.setId(resultSet.getInt("id"));
                quoteRequest.setCustomerId(resultSet.getInt("customer_id"));
                quoteRequest.setServiceId(resultSet.getInt("service_id"));
                quoteRequest.setStatus(resultSet.getString("status"));
                // Fetch additional fields if needed
                quoteRequests.add(quoteRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quoteRequests;
    }



    public List<QuoteRequest> getPendingQuoteRequests() {
        List<QuoteRequest> quoteRequests = new ArrayList<>();
        try {
            String query = "SELECT q.id AS quote_id, q.customer_id, q.service_id, q.status, q.request_date, c.full_name AS customer_name, c.address AS customer_address, c.phone AS customer_phone, s.name AS service_name FROM quote_requests q JOIN services s ON q.service_id = s.id JOIN customers c ON q.customer_id = c.id WHERE q.status = 'pending';";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Print column names for debugging
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " + metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                QuoteRequest quoteRequest = new QuoteRequest();
                quoteRequest.setId(resultSet.getInt("quote_id"));
                quoteRequest.setCustomerId(resultSet.getInt("customer_id"));
                quoteRequest.setServiceId(resultSet.getInt("service_id"));
                quoteRequest.setStatus(resultSet.getString("status"));
                quoteRequest.setServiceName(resultSet.getString("service_name"));
                quoteRequest.setCustomerName(resultSet.getString("customer_name"));
                quoteRequest.setCustomerAddress(resultSet.getString("customer_address"));
                quoteRequest.setCustomerPhone(resultSet.getString("customer_phone"));
                quoteRequests.add(quoteRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quoteRequests;
    }

}
