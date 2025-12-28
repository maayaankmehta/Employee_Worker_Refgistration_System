package com.employeemanagement.ui;

import com.employeemanagement.dao.DepartmentDAO;
import com.employeemanagement.dao.EmployerDAO;
import com.employeemanagement.dao.WorkerDAO;
import com.employeemanagement.model.Department;
import com.employeemanagement.model.Employer;
import com.employeemanagement.model.Worker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class WorkerPanel extends JPanel {
    private JTextField nameField, emailField, phoneField, positionField;
    private JComboBox<Employer> employerCombo;
    private JComboBox<Department> departmentCombo;
    private JSpinner hireDateSpinner;
    private JTable workerTable;
    private DefaultTableModel tableModel;
    private WorkerDAO workerDAO;
    private EmployerDAO employerDAO;
    private DepartmentDAO departmentDAO;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private int selectedId = -1;

    public WorkerPanel() {
        workerDAO = new WorkerDAO();
        employerDAO = new EmployerDAO();
        departmentDAO = new DepartmentDAO();
        initComponents();
        loadEmployers();
        loadDepartments();
        loadWorkers();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Worker Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        phoneField = new JTextField(20);
        formPanel.add(phoneField, gbc);

        // Position
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Position:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        positionField = new JTextField(20);
        formPanel.add(positionField, gbc);

        // Employer
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Employer:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        employerCombo = new JComboBox<>();
        formPanel.add(employerCombo, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        departmentCombo = new JComboBox<>();
        formPanel.add(departmentCombo, gbc);

        // Hire Date
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Hire Date:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        hireDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(hireDateSpinner, "yyyy-MM-dd");
        hireDateSpinner.setEditor(dateEditor);
        formPanel.add(hireDateSpinner, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add Worker");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addWorker());
        updateButton.addActionListener(e -> updateWorker());
        deleteButton.addActionListener(e -> deleteWorker());
        clearButton.addActionListener(e -> clearForm());

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Workers List"));

        String[] columns = { "ID", "Name", "Email", "Phone", "Position", "Employer", "Department", "Hire Date" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        workerTable = new JTable(tableModel);
        workerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && workerTable.getSelectedRow() != -1) {
                loadSelectedRow();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(workerTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadEmployers() {
        employerCombo.removeAllItems();
        List<Employer> employers = employerDAO.getAllEmployers();
        for (Employer emp : employers) {
            employerCombo.addItem(emp);
        }
    }

    private void loadDepartments() {
        departmentCombo.removeAllItems();
        List<Department> departments = departmentDAO.getAllDepartments();
        for (Department dept : departments) {
            departmentCombo.addItem(dept);
        }
    }

    private void loadWorkers() {
        tableModel.setRowCount(0);
        List<Worker> workers = workerDAO.getAllWorkers();
        for (Worker worker : workers) {
            tableModel.addRow(new Object[] {
                    worker.getId(),
                    worker.getName(),
                    worker.getEmail(),
                    worker.getPhone(),
                    worker.getPosition(),
                    worker.getEmployerName(),
                    worker.getDepartmentName(),
                    worker.getHireDate()
            });
        }
    }

    private void loadSelectedRow() {
        int selectedRow = workerTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (int) tableModel.getValueAt(selectedRow, 0);
            Worker worker = workerDAO.getWorkerById(selectedId);

            if (worker != null) {
                nameField.setText(worker.getName());
                emailField.setText(worker.getEmail());
                phoneField.setText(worker.getPhone());
                positionField.setText(worker.getPosition());

                // Select employer
                for (int i = 0; i < employerCombo.getItemCount(); i++) {
                    Employer emp = employerCombo.getItemAt(i);
                    if (emp.getId() == worker.getEmployerId()) {
                        employerCombo.setSelectedIndex(i);
                        break;
                    }
                }

                // Select department
                for (int i = 0; i < departmentCombo.getItemCount(); i++) {
                    Department dept = departmentCombo.getItemAt(i);
                    if (dept.getId() == worker.getDepartmentId()) {
                        departmentCombo.setSelectedIndex(i);
                        break;
                    }
                }

                // Set hire date
                if (worker.getHireDate() != null) {
                    hireDateSpinner.setValue(new java.util.Date(worker.getHireDate().getTime()));
                }
            }

            addButton.setEnabled(false);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    private void addWorker() {
        if (!validateForm())
            return;

        Employer employer = (Employer) employerCombo.getSelectedItem();
        Department dept = (Department) departmentCombo.getSelectedItem();
        java.util.Date utilDate = (java.util.Date) hireDateSpinner.getValue();
        Date sqlDate = new Date(utilDate.getTime());

        Worker worker = new Worker(
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                employer.getId(),
                dept.getId(),
                positionField.getText().trim(),
                sqlDate);

        if (workerDAO.addWorker(worker)) {
            JOptionPane.showMessageDialog(this, "Worker added successfully!");
            clearForm();
            loadWorkers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add worker!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateWorker() {
        if (!validateForm())
            return;

        Employer employer = (Employer) employerCombo.getSelectedItem();
        Department dept = (Department) departmentCombo.getSelectedItem();
        java.util.Date utilDate = (java.util.Date) hireDateSpinner.getValue();
        Date sqlDate = new Date(utilDate.getTime());

        Worker worker = new Worker(
                selectedId,
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                employer.getId(),
                dept.getId(),
                positionField.getText().trim(),
                sqlDate);

        if (workerDAO.updateWorker(worker)) {
            JOptionPane.showMessageDialog(this, "Worker updated successfully!");
            clearForm();
            loadWorkers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update worker!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteWorker() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this worker?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (workerDAO.deleteWorker(selectedId)) {
                JOptionPane.showMessageDialog(this, "Worker deleted successfully!");
                clearForm();
                loadWorkers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete worker!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter name!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (positionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter position!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (employerCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select an employer!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (departmentCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a department!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        positionField.setText("");
        if (employerCombo.getItemCount() > 0) {
            employerCombo.setSelectedIndex(0);
        }
        if (departmentCombo.getItemCount() > 0) {
            departmentCombo.setSelectedIndex(0);
        }
        hireDateSpinner.setValue(new java.util.Date());
        selectedId = -1;
        workerTable.clearSelection();

        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public void refresh() {
        loadEmployers();
        loadDepartments();
        loadWorkers();
    }
}
