package com.codeflo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Handles PostgreSQL database connection and operations.
 */
public class PostgresDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/codeflo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "yourpassword";

    private Connection connection;

    public PostgresDB() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertFileMetadata(String fileName, String filePath, String language) {
        String query = "INSERT INTO file_metadata (file_name, file_path, language) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fileName);
            stmt.setString(2, filePath);
            stmt.setString(3, language);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
