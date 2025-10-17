package dao;

import database.DatabaseConnection;
import models.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments(member_id, amount, payment_date, payment_type) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getMemberId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setString(3, java.time.LocalDate.now().toString());
            pstmt.setString(4, payment.getPaymentType());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Payment> getMemberPayments(int memberId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE member_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setMemberId(rs.getInt("member_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getString("payment_date"));
                payment.setPaymentType(rs.getString("payment_type"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}