package database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gym_management";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "jawad2005";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection( "jdbc:mysql://localhost:3306/gym_management", "admin", "jawad2005");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public static void initializeDatabase() {
        // First create database if not exists
        createDatabase();

        String[] createTables = {
            // Users table
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "username VARCHAR(50) UNIQUE NOT NULL," +
            "password VARCHAR(255) NOT NULL," +
            "user_type ENUM('ADMIN', 'TRAINER', 'MEMBER') NOT NULL," +
            "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",

            // Members table
            "CREATE TABLE IF NOT EXISTS members (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(100) NOT NULL," +
            "phone VARCHAR(15) UNIQUE," +
            "email VARCHAR(100) UNIQUE," +
            "address TEXT," +
            "age INT," +
            "gender ENUM('Male', 'Female', 'Other')," +
            "join_date DATE," +
            "membership_type VARCHAR(50)," +
            "membership_start_date DATE," +
            "membership_end_date DATE," +
            "status ENUM('Active', 'Inactive', 'Suspended') DEFAULT 'Active'," +
            "emergency_contact VARCHAR(15)," +
            "medical_conditions TEXT," +
            "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)",

            // Add other tables as needed...
        };

        try (Connection conn = getConnection()) {
            // Create tables
            for (String sql : createTables) {
                try {
                    conn.createStatement().execute(sql);
                } catch (SQLException e) {
                    System.err.println("Error creating table: " + e.getMessage());
                }
            }

            // Insert default users
            insertDefaultUsers(conn);

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createDatabase() {
        try {
            // Connect without specific database to create it
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:5366/", USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS gym_management");
            conn.close();
            System.out.println(" Database 'gym_management' created/verified");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    private static void insertDefaultUsers(Connection conn) throws SQLException {
        // Check if users already exist
        String checkUsers = "SELECT COUNT(*) FROM users";
        ResultSet rs = conn.createStatement().executeQuery(checkUsers);

        if (rs.next() && rs.getInt(1) == 0) {
            // Insert default users
            String insertUsers = "INSERT INTO users (username, password, user_type) VALUES " +
                "('admin', 'admin123', 'ADMIN'), " +
                "('trainer', 'trainer123', 'TRAINER'), " +
                "('member', 'member123', 'MEMBER')";
            conn.createStatement().execute(insertUsers);
            System.out.println("Default users inserted");
        } else {
            System.out.println("️ Users already exist in database");
        }
    }
}