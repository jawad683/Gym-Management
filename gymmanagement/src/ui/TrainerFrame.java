package ui;

import dao.TrainerDAO;
import models.Trainer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TrainerFrame extends JFrame {
    private TrainerDAO trainerDAO;
    private JTable trainerTable;
    private DefaultTableModel tableModel;

    public TrainerFrame() {
        trainerDAO = new TrainerDAO();
        setupUI();
        loadTrainers();
    }

    private void setupUI() {
        setTitle("Trainer Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        Color darkBg = new Color(45, 45, 45);
        Color panelBg = new Color(60, 63, 65);
        Color textColor = Color.BLACK;
        Color buttonColor = new Color(193, 180, 180);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Trainer Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(buttonColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Name", "Specialization", "Phone", "Salary (₹)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        trainerTable = new JTable(tableModel);
        trainerTable.setBackground(panelBg);
        trainerTable.setForeground(textColor);
        trainerTable.setSelectionBackground(buttonColor);

        JScrollPane tableScroll = new JScrollPane(trainerTable);
        tableScroll.getViewport().setBackground(panelBg);
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(darkBg);

        JButton addBtn = createButton("Add Trainer");
        JButton updateBtn = createButton("Update Trainer");
        JButton deleteBtn = createButton("Delete Trainer");
        JButton paymentBtn = createButton("Payment Status");
        JButton refreshBtn = createButton("Refresh");

        addBtn.addActionListener(e -> showAddTrainerDialog());
        updateBtn.addActionListener(e -> showUpdateTrainerDialog());
        deleteBtn.addActionListener(e -> deleteTrainer());
        paymentBtn.addActionListener(e -> showPaymentStatus());
        refreshBtn.addActionListener(e -> loadTrainers());

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

    private void loadTrainers() {
        tableModel.setRowCount(0);
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        for (Trainer trainer : trainers) {
            tableModel.addRow(new Object[]{
                    trainer.getId(),
                    trainer.getName(),
                    trainer.getSpecialization(),
                    trainer.getPhone(),
                    String.format("₹%.2f", trainer.getSalary())
            });
        }
    }

    private void showAddTrainerDialog() {
        JTextField nameField = new JTextField();
        JTextField specializationField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Specialization:", specializationField,
                "Phone:", phoneField,
                "Monthly Salary (₹):", salaryField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Trainer",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                double salary = Double.parseDouble(salaryField.getText());
                Trainer trainer = new Trainer(
                        nameField.getText(),
                        specializationField.getText(),
                        phoneField.getText(),
                        salary
                );

                if (trainerDAO.addTrainer(trainer)) {
                    JOptionPane.showMessageDialog(this, "Trainer added successfully!");
                    loadTrainers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add trainer!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid salary!");
            }
        }
    }

    private void showUpdateTrainerDialog() {
        int selectedRow = trainerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer to update!");
            return;
        }

        int trainerId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentName = (String) tableModel.getValueAt(selectedRow, 1);
        String currentSpecialization = (String) tableModel.getValueAt(selectedRow, 2);
        String currentPhone = (String) tableModel.getValueAt(selectedRow, 3);
        String currentSalary = ((String) tableModel.getValueAt(selectedRow, 4)).replace("₹", "");

        JTextField nameField = new JTextField(currentName);
        JTextField specializationField = new JTextField(currentSpecialization);
        JTextField phoneField = new JTextField(currentPhone);
        JTextField salaryField = new JTextField(currentSalary);

        Object[] message = {
                "Name:", nameField,
                "Specialization:", specializationField,
                "Phone:", phoneField,
                "Monthly Salary (₹):", salaryField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Trainer",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                double salary = Double.parseDouble(salaryField.getText());
                Trainer trainer = new Trainer();
                trainer.setId(trainerId);
                trainer.setName(nameField.getText());
                trainer.setSpecialization(specializationField.getText());
                trainer.setPhone(phoneField.getText());
                trainer.setSalary(salary);

                if (trainerDAO.updateTrainer(trainer)) {
                    JOptionPane.showMessageDialog(this, "Trainer updated successfully!");
                    loadTrainers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update trainer!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid salary!");
            }
        }
    }

    private void deleteTrainer() {
        int selectedRow = trainerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a trainer to delete!");
            return;
        }

        int trainerId = (int) tableModel.getValueAt(selectedRow, 0);
        String trainerName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + trainerName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (trainerDAO.deleteTrainer(trainerId)) {
                JOptionPane.showMessageDialog(this, "Trainer deleted successfully!");
                loadTrainers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete trainer!");
            }
        }
    }

    private void showPaymentStatus() {
        StringBuilder status = new StringBuilder("Trainer Payment Status:\n\n");
        List<Trainer> trainers = trainerDAO.getAllTrainers();

        for (Trainer trainer : trainers) {
            status.append(String.format("• %s (ID: %d) - Monthly Salary: ₹%.2f\n",
                    trainer.getName(), trainer.getId(), trainer.getSalary()));
        }

        status.append("\nNext payment date: 1st of next month");

        JTextArea textArea = new JTextArea(status.toString());
        textArea.setEditable(false);
        textArea.setBackground(new Color(60, 63, 65));
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Payment Status",
                JOptionPane.INFORMATION_MESSAGE);
    }
}