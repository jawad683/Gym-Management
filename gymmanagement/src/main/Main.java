package main;

import database.DatabaseConnection;
import ui.LoginFrame;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Setup UI theme
        setupDarkTheme();

        System.out.println("🚀 Starting Gym Management System...");

        // Initialize database directly
        System.out.println("📊 Initializing database...");
        DatabaseConnection.initializeDatabase();

        // Start application
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
            System.out.println("✅ Application started successfully!");
            System.out.println("👤 Login Credentials:");
            System.out.println("   Admin:   username='admin', password='admin123'");
            System.out.println("   Trainer: username='trainer', password='trainer123'");
            System.out.println("   Member:  username='member', password='member123'");
        });
    }

    private static void setupDarkTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Dark theme setup
            Color darkBackground = new Color(129, 121, 121);
            Color darkPanel = new Color(201, 201, 220);
            Color textColor = Color.WHITE;
            Color buttonColor = new Color(183, 192, 207);

            UIManager.put("Panel.background", darkBackground);
            UIManager.put("Button.background", buttonColor);
            UIManager.put("Button.foreground", textColor);
            UIManager.put("Label.foreground", textColor);
            UIManager.put("TextField.background", darkBackground);
            UIManager.put("TextField.foreground", textColor);
            UIManager.put("PasswordField.background", darkPanel);
            UIManager.put("PasswordField.foreground", textColor);
            UIManager.put("ComboBox.background", darkPanel);
            UIManager.put("ComboBox.foreground", textColor);

        } catch (Exception e) {
            System.err.println("Warning: Could not set look and feel");
        }
    }
}