package com.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    // replace with your credentials
    private static final String URL = "jdbc:mysql://localhost:3306/yourDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
