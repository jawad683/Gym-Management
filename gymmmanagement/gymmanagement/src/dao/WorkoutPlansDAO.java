package dao;

import database.DatabaseConnection;
import models.WorkoutPlans;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutPlansDAO {

    // Create workout plan table if not exists
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS workout_plans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "plan_name TEXT NOT NULL," +
                "duration TEXT NOT NULL," +
                "price INTEGER NOT NULL," +
                "description TEXT," +
                "features TEXT," +
                "status TEXT DEFAULT 'Active'," +
                "created_date TEXT DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            insertDefaultPlans(conn);
        } catch (SQLException e) {
            System.err.println("Error creating workout_plans table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Insert default workout plans
    private static void insertDefaultPlans(Connection conn) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM workout_plans";
        ResultSet rs = conn.createStatement().executeQuery(checkSql);

        if (rs.getInt(1) == 0) {
            String insertSql = "INSERT INTO workout_plans (plan_name, duration, price, description, features) VALUES " +
                    "('Basic Plan', '1 Month', 1500, '1 Month Basic Membership', 'Gym Access, Basic Equipment, Locker Room'), " +
                    "('Premium Plan', '1 Month', 2500, '1 Month Premium Membership', 'Gym Access, All Equipment, 1 PT Session'), " +
                    "('Basic Quarterly', '3 Months', 4000, '3 Months Basic Membership', 'Gym Access, All Equipment, 2 PT Sessions'), " +
                    "('Premium Quarterly', '3 Months', 6500, '3 Months Premium Membership', 'Full Access, Personal Trainer, Diet Plan'), " +
                    "('Basic Yearly', '1 Year', 12000, '1 Year Basic Membership', 'Gym Access, Monthly Checkup, Locker'), " +
                    "('Premium Yearly', '1 Year', 20000, '1 Year Premium Membership', 'Full Access, Unlimited PT, Diet & Supplement Guidance')";
            conn.createStatement().execute(insertSql);
        }
    }

    // Add new workout plan
    public boolean addWorkoutPlan(WorkoutPlans plan) {
        String sql = "INSERT INTO workout_plans (plan_name, duration, price, description, features, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plan.getPlanName());
            pstmt.setString(2, plan.getDuration());
            pstmt.setInt(3, plan.getPrice());
            pstmt.setString(4, plan.getDescription());
            pstmt.setString(5, plan.getFeatures());
            pstmt.setString(6, plan.getStatus());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding workout plan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get all workout plans
    public List<WorkoutPlans> getAllWorkoutPlans() {
        List<WorkoutPlans> plans = new ArrayList<>();
        String sql = "SELECT * FROM workout_plans ORDER BY price";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                plans.add(mapResultSetToWorkoutPlan(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all workout plans: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }

    // Get active workout plans only
    public List<WorkoutPlans> getActiveWorkoutPlans() {
        List<WorkoutPlans> plans = new ArrayList<>();
        String sql = "SELECT * FROM workout_plans WHERE status = 'Active' ORDER BY price";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                plans.add(mapResultSetToWorkoutPlan(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting active workout plans: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }

    // Get workout plans by duration
    public List<WorkoutPlans> getWorkoutPlansByDuration(String duration) {
        List<WorkoutPlans> plans = new ArrayList<>();
        String sql = "SELECT * FROM workout_plans WHERE duration = ? AND status = 'Active' ORDER BY price";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, duration);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                plans.add(mapResultSetToWorkoutPlan(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting workout plans by duration: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }

    // Get workout plan by ID
    public WorkoutPlans getWorkoutPlanById(int id) {
        String sql = "SELECT * FROM workout_plans WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToWorkoutPlan(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error getting workout plan by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Update workout plan
    public boolean updateWorkoutPlan(WorkoutPlans plan) {
        String sql = "UPDATE workout_plans SET plan_name = ?, duration = ?, price = ?, " +
                "description = ?, features = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plan.getPlanName());
            pstmt.setString(2, plan.getDuration());
            pstmt.setInt(3, plan.getPrice());
            pstmt.setString(4, plan.getDescription());
            pstmt.setString(5, plan.getFeatures());
            pstmt.setString(6, plan.getStatus());
            pstmt.setInt(7, plan.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating workout plan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete workout plan
    public boolean deleteWorkoutPlan(int id) {
        String sql = "DELETE FROM workout_plans WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting workout plan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get monthly plans
    public List<WorkoutPlans> getMonthlyPlans() {
        return getWorkoutPlansByDuration("1 Month");
    }

    // Get quarterly plans
    public List<WorkoutPlans> getQuarterlyPlans() {
        return getWorkoutPlansByDuration("3 Months");
    }

    // Get yearly plans
    public List<WorkoutPlans> getYearlyPlans() {
        return getWorkoutPlansByDuration("1 Year");
    }

    // Search workout plans by name
    public List<WorkoutPlans> searchWorkoutPlans(String searchTerm) {
        List<WorkoutPlans> plans = new ArrayList<>();
        String sql = "SELECT * FROM workout_plans WHERE plan_name LIKE ? OR description LIKE ? ORDER BY price";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                plans.add(mapResultSetToWorkoutPlan(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching workout plans: " + e.getMessage());
            e.printStackTrace();
        }
        return plans;
    }

    // Get plan count by status
    public int getPlanCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM workout_plans WHERE status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error getting plan count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to map ResultSet to WorkoutPlan object
    private WorkoutPlans mapResultSetToWorkoutPlan(ResultSet rs) throws SQLException {
        WorkoutPlans plan = new WorkoutPlans();
        plan.setId(rs.getInt("id"));
        plan.setPlanName(rs.getString("plan_name"));
        plan.setDuration(rs.getString("duration"));
        plan.setPrice(rs.getInt("price"));
        plan.setDescription(rs.getString("description"));
        plan.setFeatures(rs.getString("features"));
        plan.setStatus(rs.getString("status"));
        return plan;
    }
}