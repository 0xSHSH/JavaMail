package com.javamail.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Thread-safe JDBC connection utility.
 * Returns a NEW connection per call (connection-per-request pattern).
 * Safe for concurrent servlet requests.
 */
public class DBConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/javamail_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "root";  // Change to your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    private DBConnection() {}

    /**
     * Returns a fresh Connection for each call.
     * Caller MUST close it (use try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
