package com.employeemanagement.ui;

import com.employeemanagement.dao.WorkerDAO;
import com.employeemanagement.model.Worker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewRecordsPanel extends JPanel {
    private JTable recordsTable;
    private DefaultTableModel tableModel;
    private WorkerDAO workerDAO;
    private JTextField searchField;
    private JButton refreshButton, searchButton;

    public ViewRecordsPanel() {
        workerDAO = new WorkerDAO();
        initComponents();
        loadRecords();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel with search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchRecords());
        topPanel.add(searchButton);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadRecords());
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Complete Records View"));

        String[] columns = { "Worker ID", "Worker Name", "Email", "Phone", "Position",
                "Hire Date", "Employer", "Department" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recordsTable = new JTable(tableModel);
        recordsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane tableScrollPane = new JScrollPane(recordsTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel with info
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Total Records: 0");
        bottomPanel.add(infoLabel);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadRecords() {
        tableModel.setRowCount(0);
        List<Worker> workers = workerDAO.getAllWorkers();

        for (Worker worker : workers) {
            tableModel.addRow(new Object[] {
                    worker.getId(),
                    worker.getName(),
                    worker.getEmail(),
                    worker.getPhone(),
                    worker.getPosition(),
                    worker.getHireDate(),
                    worker.getEmployerName(),
                    worker.getDepartmentName()
            });
        }

        updateRecordCount();
    }

    private void searchRecords() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadRecords();
            return;
        }

        tableModel.setRowCount(0);
        List<Worker> workers = workerDAO.getAllWorkers();

        for (Worker worker : workers) {
            if (matchesSearch(worker, searchText)) {
                tableModel.addRow(new Object[] {
                        worker.getId(),
                        worker.getName(),
                        worker.getEmail(),
                        worker.getPhone(),
                        worker.getPosition(),
                        worker.getHireDate(),
                        worker.getEmployerName(),
                        worker.getDepartmentName()
                });
            }
        }

        updateRecordCount();
    }

    private boolean matchesSearch(Worker worker, String searchText) {
        return (worker.getName() != null && worker.getName().toLowerCase().contains(searchText)) ||
                (worker.getEmail() != null && worker.getEmail().toLowerCase().contains(searchText)) ||
                (worker.getPosition() != null && worker.getPosition().toLowerCase().contains(searchText)) ||
                (worker.getEmployerName() != null && worker.getEmployerName().toLowerCase().contains(searchText)) ||
                (worker.getDepartmentName() != null && worker.getDepartmentName().toLowerCase().contains(searchText));
    }

    private void updateRecordCount() {
        JPanel bottomPanel = (JPanel) ((JPanel) getComponent(1)).getComponent(1);
        JLabel infoLabel = (JLabel) bottomPanel.getComponent(0);
        infoLabel.setText("Total Records: " + tableModel.getRowCount());
    }

    public void refresh() {
        loadRecords();
    }
}
