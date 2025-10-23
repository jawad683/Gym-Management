package ui;

import dao.MemberDAO;
import dao.PaymentDAO;
import models.Member;
import models.Payment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberFrame extends JFrame {
    private MemberDAO memberDAO;
    private PaymentDAO paymentDAO;
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public MemberFrame() {
        memberDAO = new MemberDAO();
        paymentDAO = new PaymentDAO();
        setupUI();
        loadMembers();
    }

    private void setupUI() {
        setTitle("Member Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        Color darkBg = new Color(45, 45, 45);
        Color panelBg = new Color(60, 63, 65);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(75, 110, 175);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Member Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(buttonColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Phone", "Email", "Join Date", "Membership Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(tableModel);
        memberTable.setBackground(panelBg);
        memberTable.setForeground(textColor);
        memberTable.setSelectionBackground(buttonColor);
        memberTable.setGridColor(Color.GRAY);

        JScrollPane tableScroll = new JScrollPane(memberTable);
        tableScroll.getViewport().setBackground(panelBg);
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(darkBg);

        JButton addBtn = createButton("Add Member");
        JButton updateBtn = createButton("Update Member");
        JButton deleteBtn = createButton("Delete Member");
        JButton paymentBtn = createButton("Add Payment");
        JButton refreshBtn = createButton("Refresh");

        addBtn.addActionListener(e -> showAddMemberDialog());
        updateBtn.addActionListener(e -> showUpdateMemberDialog());
        deleteBtn.addActionListener(e -> deleteMember());
        paymentBtn.addActionListener(e -> showPaymentDialog());
        refreshBtn.addActionListener(e -> loadMembers());

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(paymentBtn);
        buttonPanel.add(refreshBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(75, 110, 175));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void loadMembers() {
        tableModel.setRowCount(0);
        List<Member> members = memberDAO.getAllMembers();
        for (Member member : members) {
            tableModel.addRow(new Object[]{
                    member.getId(),
                    member.getName(),
                    member.getPhone(),
                    member.getEmail(),
                    member.getJoinDate(),
                    member.getMembershipType()
            });
        }
    }

    private void showAddMemberDialog() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> membershipCombo = new JComboBox<>(new String[]{
                "Monthly - ₹1500", "Quarterly - ₹4000", "Yearly - ₹12000"
        });

        Object[] message = {
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField,
                "Membership:", membershipCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Member",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Member member = new Member(
                    nameField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    membershipCombo.getSelectedItem().toString()
            );

            if (memberDAO.addMember(member)) {
                JOptionPane.showMessageDialog(this, "Member added successfully!");
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add member!");
            }
        }
    }

    private void showUpdateMemberDialog() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to update!");
            return;
        }

        int memberId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentPhone = (String) tableModel.getValueAt(selectedRow, 2);
        String currentEmail = (String) tableModel.getValueAt(selectedRow, 3);
        String currentMembership = (String) tableModel.getValueAt(selectedRow, 5);

        JTextField nameField = new JTextField(currentName);
        JTextField phoneField = new JTextField(currentPhone);
        JTextField emailField = new JTextField(currentEmail);
        JComboBox<String> membershipCombo = new JComboBox<>(new String[]{
                "Monthly - ₹1500", "Quarterly - ₹4000", "Yearly - ₹12000"
        });
        membershipCombo.setSelectedItem(currentMembership);

        Object[] message = {
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField,
                "Membership:", membershipCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Member",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Member member = new Member();
            member.setId(memberId);
            member.setName(nameField.getText());
            member.setPhone(phoneField.getText());
            member.setEmail(emailField.getText());
            member.setMembershipType(membershipCombo.getSelectedItem().toString());

            if (memberDAO.updateMember(member)) {
                JOptionPane.showMessageDialog(this, "Member updated successfully!");
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update member!");
            }
        }
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete!");
            return;
        }

        int memberId = (int) tableModel.getValueAt(selectedRow, 0);
        String memberName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + memberName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (memberDAO.deleteMember(memberId)) {
                JOptionPane.showMessageDialog(this, "Member deleted successfully!");
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete member!");
            }
        }
    }

    private void showPaymentDialog() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member for payment!");
            return;
        }

        int memberId = (int) tableModel.getValueAt(selectedRow, 0);
        String memberName = (String) tableModel.getValueAt(selectedRow, 1);

        JComboBox<String> paymentType = new JComboBox<>(new String[]{
                "Monthly Fee", "Quarterly Fee", "Yearly Fee", "Personal Training", "Other"
        });
        JTextField amountField = new JTextField();

        Object[] message = {
                "Member: " + memberName,
                "Payment Type:", paymentType,
                "Amount (₹):", amountField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Payment",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Payment payment = new Payment(memberId, amount,
                        paymentType.getSelectedItem().toString());

                if (paymentDAO.addPayment(payment)) {
                    JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to record payment!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount!");
            }
        }
    }
}