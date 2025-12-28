package com.employeemanagement;

import com.employeemanagement.dao.DatabaseConnection;
import com.employeemanagement.ui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Test database connection
        SwingUtilities.invokeLater(() -> {
            if (!DatabaseConnection.testConnection()) {
                JOptionPane.showMessageDialog(null,
                        "Failed to connect to database!\n\n" +
                                "Please ensure:\n" +
                                "1. MySQL is running\n" +
                                "2. Database 'employee_worker_db' exists\n" +
                                "3. Credentials in DatabaseConnection.java are correct\n\n" +
                                "You can create the database by running:\n" +
                                "sql/schema.sql in MySQL",
                        "Database Connection Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and show the main frame
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
