package com.employeemanagement.ui;

import com.employeemanagement.dao.DepartmentDAO;
import com.employeemanagement.dao.EmployerDAO;
import com.employeemanagement.model.Department;
import com.employeemanagement.model.Employer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployerPanel extends JPanel {
    private JTextField nameField, emailField, phoneField, companyField;
    private JComboBox<Department> departmentCombo;
    private JTable employerTable;
    private DefaultTableModel tableModel;
    private EmployerDAO employerDAO;
    private DepartmentDAO departmentDAO;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private int selectedId = -1;

    public EmployerPanel() {
        employerDAO = new EmployerDAO();
        departmentDAO = new DepartmentDAO();
        initComponents();
        loadDepartments();
        loadEmployers();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Employer Information"));
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

        // Company
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Company Name:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        companyField = new JTextField(20);
        formPanel.add(companyField, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        departmentCombo = new JComboBox<>();
        formPanel.add(departmentCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add Employer");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addEmployer());
        updateButton.addActionListener(e -> updateEmployer());
        deleteButton.addActionListener(e -> deleteEmployer());
        clearButton.addActionListener(e -> clearForm());

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Employers List"));

        String[] columns = { "ID", "Name", "Email", "Phone", "Company", "Department" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employerTable = new JTable(tableModel);
        employerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && employerTable.getSelectedRow() != -1) {
                loadSelectedRow();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(employerTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDepartments() {
        departmentCombo.removeAllItems();
        List<Department> departments = departmentDAO.getAllDepartments();
        for (Department dept : departments) {
            departmentCombo.addItem(dept);
        }
    }

    private void loadEmployers() {
        tableModel.setRowCount(0);
        List<Employer> employers = employerDAO.getAllEmployers();
        for (Employer emp : employers) {
            tableModel.addRow(new Object[] {
                    emp.getId(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getPhone(),
                    emp.getCompanyName(),
                    emp.getDepartmentName()
            });
        }
    }

    private void loadSelectedRow() {
        int selectedRow = employerTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (int) tableModel.getValueAt(selectedRow, 0);
            nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            emailField.setText((String) tableModel.getValueAt(selectedRow, 2));
            phoneField.setText((String) tableModel.getValueAt(selectedRow, 3));
            companyField.setText((String) tableModel.getValueAt(selectedRow, 4));

            // Select department in combo
            Employer emp = employerDAO.getEmployerById(selectedId);
            if (emp != null) {
                for (int i = 0; i < departmentCombo.getItemCount(); i++) {
                    Department dept = departmentCombo.getItemAt(i);
                    if (dept.getId() == emp.getDepartmentId()) {
                        departmentCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }

            addButton.setEnabled(false);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    private void addEmployer() {
        if (!validateForm())
            return;

        Department dept = (Department) departmentCombo.getSelectedItem();
        Employer employer = new Employer(
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                companyField.getText().trim(),
                dept.getId());

        if (employerDAO.addEmployer(employer)) {
            JOptionPane.showMessageDialog(this, "Employer added successfully!");
            clearForm();
            loadEmployers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employer!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployer() {
        if (!validateForm())
            return;

        Department dept = (Department) departmentCombo.getSelectedItem();
        Employer employer = new Employer(
                selectedId,
                nameField.getText().trim(),
                emailField.getText().trim(),
                phoneField.getText().trim(),
                companyField.getText().trim(),
                dept.getId());

        if (employerDAO.updateEmployer(employer)) {
            JOptionPane.showMessageDialog(this, "Employer updated successfully!");
            clearForm();
            loadEmployers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employer!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployer() {
        int workerCount = employerDAO.getWorkerCount(selectedId);
        if (workerCount > 0) {
            JOptionPane.showMessageDialog(this,
                    "Cannot delete employer with " + workerCount + " registered worker(s)!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this employer?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (employerDAO.deleteEmployer(selectedId)) {
                JOptionPane.showMessageDialog(this, "Employer deleted successfully!");
                clearForm();
                loadEmployers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employer!",
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
        if (companyField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter company name!",
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
        companyField.setText("");
        if (departmentCombo.getItemCount() > 0) {
            departmentCombo.setSelectedIndex(0);
        }
        selectedId = -1;
        employerTable.clearSelection();

        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public void refresh() {
        loadDepartments();
        loadEmployers();
    }
}
