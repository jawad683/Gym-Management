package ui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AttendanceFrame extends JFrame {
    private JTable attendanceTable;
    private JTextField memberIdField;

    public AttendanceFrame() {
        setupUI();
        loadAttendance();
    }

    private void setupUI() {
        setTitle("Manage Attendance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Color buttonColor = new Color(75, 110, 175);

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Mark Attendance"));

        topPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField();
        topPanel.add(memberIdField);

        JButton checkInButton = new JButton("Check In");
        checkInButton.setBackground(buttonColor);
        checkInButton.setForeground(Color.WHITE);
        checkInButton.addActionListener(e -> markCheckIn());
        topPanel.add(checkInButton);

        JButton checkOutButton = new JButton("Check Out");
        checkOutButton.setBackground(buttonColor);
        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.addActionListener(e -> markCheckOut());
        topPanel.add(checkOutButton);

        add(topPanel, BorderLayout.NORTH);

        attendanceTable = new JTable();
        add(new JScrollPane(attendanceTable), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void markCheckIn() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO attendance (member_id, check_in, date) VALUES (?, NOW(), CURDATE())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(memberIdField.getText()));
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Check-in marked successfully!");
            loadAttendance();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error marking check-in!");
        }
    }

    private void markCheckOut() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE attendance SET check_out = NOW() WHERE member_id = ? AND date = CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(memberIdField.getText()));
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Check-out marked successfully!");
            loadAttendance();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error marking check-out!");
        }
    }

    private void loadAttendance() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM attendance";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            attendanceTable.setModel(PaymentFrame.buildTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}