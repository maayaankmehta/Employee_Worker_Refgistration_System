package com.employeemanagement.ui;

import com.employeemanagement.dao.DepartmentDAO;
import com.employeemanagement.model.Department;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private DepartmentDAO departmentDAO;
    private JButton addButton, updateButton, deleteButton, clearButton;
    private int selectedId = -1;

    public DepartmentPanel() {
        departmentDAO = new DepartmentDAO();
        initComponents();
        loadDepartments();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Department Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Department Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add Department");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addDepartment());
        updateButton.addActionListener(e -> updateDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        clearButton.addActionListener(e -> clearForm());

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Departments List"));

        String[] columns = { "ID", "Name", "Description" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentTable = new JTable(tableModel);
        departmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && departmentTable.getSelectedRow() != -1) {
                loadSelectedRow();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(departmentTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    private void loadDepartments() {
        tableModel.setRowCount(0);
        List<Department> departments = departmentDAO.getAllDepartments();
        for (Department dept : departments) {
            tableModel.addRow(new Object[] {
                    dept.getId(),
                    dept.getName(),
                    dept.getDescription()
            });
        }
    }

    private void loadSelectedRow() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (int) tableModel.getValueAt(selectedRow, 0);
            nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            descriptionArea.setText((String) tableModel.getValueAt(selectedRow, 2));

            addButton.setEnabled(false);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    private void addDepartment() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter department name!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Department dept = new Department(name, description);
        if (departmentDAO.addDepartment(dept)) {
            JOptionPane.showMessageDialog(this, "Department added successfully!");
            clearForm();
            loadDepartments();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add department!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDepartment() {
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter department name!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Department dept = new Department(selectedId, name, description);
        if (departmentDAO.updateDepartment(dept)) {
            JOptionPane.showMessageDialog(this, "Department updated successfully!");
            clearForm();
            loadDepartments();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update department!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDepartment() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this department?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (departmentDAO.deleteDepartment(selectedId)) {
                JOptionPane.showMessageDialog(this, "Department deleted successfully!");
                clearForm();
                loadDepartments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete department!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
        selectedId = -1;
        departmentTable.clearSelection();

        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public void refresh() {
        loadDepartments();
    }
}
