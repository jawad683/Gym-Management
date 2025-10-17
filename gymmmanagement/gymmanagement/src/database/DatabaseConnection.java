package database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gym_management";
    private static final String USER = "root";
    private static final String PASSWORD = "jawad2005"; // put your MySQL password here

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        String[] createTables = {
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "username VARCHAR(50) UNIQUE NOT NULL," +
                        "password VARCHAR(50) NOT NULL," +
                        "user_type VARCHAR(20) NOT NULL)",

                "CREATE TABLE IF NOT EXISTS members (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "phone VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "join_date DATE," +
                        "membership_type VARCHAR(50))",

                "CREATE TABLE IF NOT EXISTS trainers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "specialization VARCHAR(100)," +
                        "phone VARCHAR(20)," +
                        "salary DOUBLE)",

                "CREATE TABLE IF NOT EXISTS payments (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "member_id INT," +
                        "amount DOUBLE," +
                        "payment_date DATE," +
                        "payment_type VARCHAR(50)," +
                        "FOREIGN KEY(member_id) REFERENCES members(id))",

                "CREATE TABLE IF NOT EXISTS attendance (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "member_id INT," +
                        "check_in DATETIME," +
                        "check_out DATETIME," +
                        "date DATE," +
                        "FOREIGN KEY(member_id) REFERENCES members(id))",

                "CREATE TABLE IF NOT EXISTS workout_plans (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "member_id INT," +
                        "plan_type VARCHAR(100)," +
                        "duration VARCHAR(50)," +
                        "exercises TEXT," +
                        "start_date DATE," +
                        "FOREIGN KEY(member_id) REFERENCES members(id))"
        };

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            for (String sql : createTables) {
                stmt.execute(sql);
            }

            // Insert default users
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO users (username, password, user_type) VALUES " +
                        "('admin', 'admin123', 'ADMIN'), " +
                        "('trainer', 'trainer123', 'TRAINER')");
            }

            System.out.println("✅ Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
