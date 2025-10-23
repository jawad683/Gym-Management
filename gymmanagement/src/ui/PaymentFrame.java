package ui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PaymentFrame extends JFrame {
    private JTable paymentTable;
    private JTextField memberIdField, amountField, typeField;

    public PaymentFrame() {
        setupUI();
        loadPayments();
    }

    private void setupUI() {
        setTitle("Manage Payments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Color bg = new Color(240, 240, 240);
        Color buttonColor = new Color(75, 110, 175);

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Add Payment"));
        topPanel.setBackground(bg);

        topPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField();
        topPanel.add(memberIdField);

        topPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        topPanel.add(amountField);

        topPanel.add(new JLabel("Payment Type:"));
        typeField = new JTextField();
        topPanel.add(typeField);

        JButton addButton = new JButton("Add Payment");
        addButton.setBackground(buttonColor);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addPayment());
        topPanel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        // Table to show payments
        paymentTable = new JTable();
        add(new JScrollPane(paymentTable), BorderLayout.CENTER);

        setVisible(true);
    }

    private void addPayment() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO payments (member_id, amount, payment_date, payment_type) VALUES (?, ?, CURDATE(), ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(memberIdField.getText()));
            stmt.setDouble(2, Double.parseDouble(amountField.getText()));
            stmt.setString(3, typeField.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Payment added successfully!");
            loadPayments();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding payment!");
        }
    }

    private void loadPayments() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM payments";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            paymentTable.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Helper to convert ResultSet → JTable model
    static javax.swing.table.TableModel buildTableModel(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData metaData = rs.getMetaData();

        java.util.Vector<String> columnNames = new java.util.Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) columnNames.add(metaData.getColumnName(i));

        java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<>();
        while (rs.next()) {
            java.util.Vector<Object> vector = new java.util.Vector<>();
            for (int i = 1; i <= columnCount; i++) vector.add(rs.getObject(i));
            data.add(vector);
        }
        return new javax.swing.table.DefaultTableModel(data, columnNames);
    }
}