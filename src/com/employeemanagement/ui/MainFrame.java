package com.employeemanagement.ui;

import com.employeemanagement.dao.DatabaseConnection;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private DepartmentPanel departmentPanel;
    private EmployerPanel employerPanel;
    private WorkerPanel workerPanel;
    private ViewRecordsPanel viewRecordsPanel;

    public MainFrame() {
        initComponents();
        setTitle("Employer-Worker Registration System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem refreshMenuItem = new JMenuItem("Refresh All");
        refreshMenuItem.addActionListener(e -> refreshAllPanels());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> exitApplication());
        fileMenu.add(refreshMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create panels
        departmentPanel = new DepartmentPanel();
        employerPanel = new EmployerPanel();
        workerPanel = new WorkerPanel();
        viewRecordsPanel = new ViewRecordsPanel();

        // Add tabs
        tabbedPane.addTab("Departments", createIcon("📁"), departmentPanel, "Manage Departments");
        tabbedPane.addTab("Employers", createIcon("🏢"), employerPanel, "Register and Manage Employers");
        tabbedPane.addTab("Workers", createIcon("👷"), workerPanel, "Register and Manage Workers");
        tabbedPane.addTab("View Records", createIcon("📊"), viewRecordsPanel, "View All Records");

        // Add listener to refresh panels when switching tabs
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    departmentPanel.refresh();
                    break;
                case 1:
                    employerPanel.refresh();
                    break;
                case 2:
                    workerPanel.refresh();
                    break;
                case 3:
                    viewRecordsPanel.refresh();
                    break;
            }
        });

        add(tabbedPane, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("Ready | Database: Connected");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private Icon createIcon(String emoji) {
        return new ImageIcon(); // Placeholder - emojis in tab titles work well on modern Java
    }

    private void refreshAllPanels() {
        departmentPanel.refresh();
        employerPanel.refresh();
        workerPanel.refresh();
        viewRecordsPanel.refresh();
        JOptionPane.showMessageDialog(this, "All panels refreshed successfully!");
    }

    private void showAbout() {
        String message = "Employer-Worker Registration System\n\n" +
                "Version 1.0\n\n" +
                "A comprehensive system for managing employers,\n" +
                "workers, and departments.\n\n" +
                "© 2025 Employee Management Solutions";
        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
