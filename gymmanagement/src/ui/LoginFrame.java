/*package ui;

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

    // Initialize DB only once
    private void initializeDatabase() {
        DatabaseConnection.initializeDatabase();
    }

    private void setupUI() {
        setTitle("Gym Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // 🎨 Colors (light mode for simplicity)
        Color bgColor = new Color(202, 202, 202);
        Color panelBg = new Color(255, 255, 255);
        Color buttonColor = new Color(83, 87, 83);
        Color textColor =  new Color(131, 120, 120);

        // 🔹 Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 Title
        JLabel titleLabel = new JLabel("GYM MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(buttonColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 🔹 Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(panelBg);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(textColor);
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(textColor);
        passwordField = new JPasswordField();

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 🔹 Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        mainPanel.add(loginButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);

        add(mainPanel);
        setVisible(true);
    }

    // 🔐 Login Handler
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Login successful! Welcome, " + user.getUsername() + "!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();

            // Redirect user to dashboard
            new DashboardFrame();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

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

    // Initialize DB only once
    private void initializeDatabase() {
        DatabaseConnection.initializeDatabase();
    }

    private void setupUI() {
        setTitle("Gym Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // 🎨 Colors - Professional Gym Theme
        Color bgColor = new Color(240, 240, 240);
        Color panelBg = new Color(255, 255, 255);
        Color buttonColor = new Color(41, 128, 185);
        Color textColor = new Color(51, 51, 51);

        // 🔹 Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 Title
        JLabel titleLabel = new JLabel("GYM MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(buttonColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 🔹 Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(panelBg);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(textColor);
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(textColor);
        passwordField = new JPasswordField();

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 🔹 Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        mainPanel.add(loginButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);

        add(mainPanel);
        setVisible(true);
    }

    // 🔐 Login Handler
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Login successful! Welcome, " + user.getUsername() + "!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();

            // Redirect user to dashboard
            new DashboardFrame();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}*/
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

    // Initialize DB only once
    private void initializeDatabase() {
        DatabaseConnection.initializeDatabase();
    }

    private void setupUI() {
        setTitle("Gym Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        // 🎨 Modern Color Scheme
        Color bgColor = new Color(245, 247, 250);
        Color panelBg = new Color(255, 255, 255);
        Color buttonColor = new Color(41, 128, 185);
        Color buttonHoverColor = new Color(52, 152, 219);
        Color textColor = new Color(51, 51, 51);
        Color borderColor = new Color(220, 220, 220);

        // 🔹 Main Panel with better styling
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 🔹 Title with improved styling
        JLabel titleLabel = new JLabel("GYM MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 🔹 Form Panel with better layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panelBg);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(textColor);
        formPanel.add(userLabel, gbc);

        // Username Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        formPanel.add(usernameField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.0;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(textColor);
        formPanel.add(passLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 🔹 Login Button with hover effects
        JButton loginButton = new JButton("LOGIN") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(buttonHoverColor);
                } else {
                    g2.setColor(buttonColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        // Add hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.repaint();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.add(loginButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(loginButton);

        add(mainPanel);
        setVisible(true);
    }

    // 🔐 Login Handler
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showErrorDialog("Please enter both username and password!");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.authenticate(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this,
                        "Login successful! Welcome, " + user.getUsername() + "!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                dispose();
                // Redirect user to dashboard
                new DashboardFrame();
            } else {
                showErrorDialog("Invalid username or password!");
            }
        } catch (Exception ex) {
            showErrorDialog("Database error: " + ex.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}