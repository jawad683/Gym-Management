package dao;

import database.DatabaseConnection;
import models.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // Mark check-in for a member
    public boolean markCheckIn(int memberId) {
        String sql = "INSERT INTO attendance(member_id, check_in, date) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String currentTime = java.time.LocalTime.now().toString().substring(0, 8);
            String currentDate = java.time.LocalDate.now().toString();

            pstmt.setInt(1, memberId);
            pstmt.setString(2, currentTime);
            pstmt.setString(3, currentDate);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error marking check-in: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}