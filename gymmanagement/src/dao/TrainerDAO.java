package dao;

import database.DatabaseConnection;
import models.Trainer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAO {
    public boolean addTrainer(Trainer trainer) {
        String sql = "INSERT INTO trainers(name, specialization, phone, salary) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trainer.getName());
            pstmt.setString(2, trainer.getSpecialization());
            pstmt.setString(3, trainer.getPhone());
            pstmt.setDouble(4, trainer.getSalary());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTrainer(Trainer trainer) {
        String sql = "UPDATE trainers SET name=?, specialization=?, phone=?, salary=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trainer.getName());
            pstmt.setString(2, trainer.getSpecialization());
            pstmt.setString(3, trainer.getPhone());
            pstmt.setDouble(4, trainer.getSalary());
            pstmt.setInt(5, trainer.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTrainer(int id) {
        String sql = "DELETE FROM trainers WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM trainers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Trainer trainer = new Trainer();
                trainer.setId(rs.getInt("id"));
                trainer.setName(rs.getString("name"));
                trainer.setSpecialization(rs.getString("specialization"));
                trainer.setPhone(rs.getString("phone"));
                trainer.setSalary(rs.getDouble("salary"));
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }
}