package ui;

import dao.UserDAO;
import models.User;
import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        initializeDatabase();
        setupUI();
    }

    private void initializeDatabase() {
        DatabaseConnection.initializeDatabase();
    }

    private void setupUI() {
        setTitle("Gym Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Dark theme colors
        Color darkBg = new Color(255, 255, 255);
        Color panelBg = new Color(200, 200, 200);
        Color textColor = Color.BLACK;
        Color buttonColor = new Color(255, 0, 175);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("GYM MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(buttonColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Login form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(panelBg);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(textColor);
        usernameField = new JTextField();
        usernameField.setBackground(panelBg);
        usernameField.setForeground(textColor);
        usernameField.setCaretColor(textColor);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(textColor);
        passwordField = new JPasswordField();
        passwordField.setBackground(panelBg);
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(textColor);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());

        // Add components to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(loginButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Enter key listener for login
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            dispose();

            if ("ADMIN".equals(user.getUserType())) {
                new MemberFrame();
            } else if ("TRAINER".equals(user.getUserType())) {
                new TrainerFrame();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

