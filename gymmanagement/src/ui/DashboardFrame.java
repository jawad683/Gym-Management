package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setupUI();
    }

    private void setupUI() {
        setTitle("Gym Management System - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Color bg = new Color(245, 245, 245);
        Color buttonColor = new Color(218, 165, 165);
        Color textColor = Color.WHITE;

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(buttonColor);
        JLabel titleLabel = new JLabel("🏋 Gym Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        buttonPanel.setBackground(bg);

        JButton memberBtn = new JButton("Manage Members");
        JButton trainerBtn = new JButton("Manage Trainers");
        JButton paymentBtn = new JButton("Manage Payments");
        JButton attendanceBtn = new JButton("Track Attendance");

        JButton logoutBtn = new JButton("Logout");

        // Style all buttons
        JButton[] buttons = {memberBtn, trainerBtn, paymentBtn, attendanceBtn, logoutBtn};
        for (JButton btn : buttons) {
            btn.setBackground(buttonColor);
            btn.setForeground(buttonColor);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
        }

        // Add actions
        memberBtn.addActionListener(e -> {
            dispose();
            new MemberFrame();
        });

        trainerBtn.addActionListener(e -> {
            dispose();
            new TrainerFrame();
        });

        paymentBtn.addActionListener(e -> {
            dispose();
            new PaymentFrame();
        });

        attendanceBtn.addActionListener(e -> {
            dispose();
            new AttendanceFrame();
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Add buttons to panel
        buttonPanel.add(memberBtn);
        buttonPanel.add(trainerBtn);
        buttonPanel.add(paymentBtn);
        buttonPanel.add(attendanceBtn);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bg);
        bottomPanel.add(logoutBtn);

        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}