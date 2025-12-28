package com.employeemanagement.dao;

import com.employeemanagement.model.Worker;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {

    public boolean addWorker(Worker worker) {
        String sql = "INSERT INTO workers (name, email, phone, employer_id, department_id, position, hire_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, worker.getName());
            pstmt.setString(2, worker.getEmail());
            pstmt.setString(3, worker.getPhone());
            pstmt.setInt(4, worker.getEmployerId());
            pstmt.setInt(5, worker.getDepartmentId());
            pstmt.setString(6, worker.getPosition());
            pstmt.setDate(7, worker.getHireDate());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Worker> getAllWorkers() {
        List<Worker> workers = new ArrayList<>();
        String sql = "SELECT w.*, e.name as emp_name, d.name as dept_name FROM workers w " +
                "LEFT JOIN employers e ON w.employer_id = e.id " +
                "LEFT JOIN departments d ON w.department_id = d.id " +
                "ORDER BY w.name";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt("id"));
                worker.setName(rs.getString("name"));
                worker.setEmail(rs.getString("email"));
                worker.setPhone(rs.getString("phone"));
                worker.setEmployerId(rs.getInt("employer_id"));
                worker.setDepartmentId(rs.getInt("department_id"));
                worker.setPosition(rs.getString("position"));
                worker.setHireDate(rs.getDate("hire_date"));
                worker.setEmployerName(rs.getString("emp_name"));
                worker.setDepartmentName(rs.getString("dept_name"));
                workers.add(worker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }

    public Worker getWorkerById(int id) {
        String sql = "SELECT w.*, e.name as emp_name, d.name as dept_name FROM workers w " +
                "LEFT JOIN employers e ON w.employer_id = e.id " +
                "LEFT JOIN departments d ON w.department_id = d.id " +
                "WHERE w.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt("id"));
                worker.setName(rs.getString("name"));
                worker.setEmail(rs.getString("email"));
                worker.setPhone(rs.getString("phone"));
                worker.setEmployerId(rs.getInt("employer_id"));
                worker.setDepartmentId(rs.getInt("department_id"));
                worker.setPosition(rs.getString("position"));
                worker.setHireDate(rs.getDate("hire_date"));
                worker.setEmployerName(rs.getString("emp_name"));
                worker.setDepartmentName(rs.getString("dept_name"));
                return worker;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Worker> getWorkersByEmployer(int employerId) {
        List<Worker> workers = new ArrayList<>();
        String sql = "SELECT w.*, e.name as emp_name, d.name as dept_name FROM workers w " +
                "LEFT JOIN employers e ON w.employer_id = e.id " +
                "LEFT JOIN departments d ON w.department_id = d.id " +
                "WHERE w.employer_id = ? ORDER BY w.name";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt("id"));
                worker.setName(rs.getString("name"));
                worker.setEmail(rs.getString("email"));
                worker.setPhone(rs.getString("phone"));
                worker.setEmployerId(rs.getInt("employer_id"));
                worker.setDepartmentId(rs.getInt("department_id"));
                worker.setPosition(rs.getString("position"));
                worker.setHireDate(rs.getDate("hire_date"));
                worker.setEmployerName(rs.getString("emp_name"));
                worker.setDepartmentName(rs.getString("dept_name"));
                workers.add(worker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }

    public List<Worker> getWorkersByDepartment(int departmentId) {
        List<Worker> workers = new ArrayList<>();
        String sql = "SELECT w.*, e.name as emp_name, d.name as dept_name FROM workers w " +
                "LEFT JOIN employers e ON w.employer_id = e.id " +
                "LEFT JOIN departments d ON w.department_id = d.id " +
                "WHERE w.department_id = ? ORDER BY w.name";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt("id"));
                worker.setName(rs.getString("name"));
                worker.setEmail(rs.getString("email"));
                worker.setPhone(rs.getString("phone"));
                worker.setEmployerId(rs.getInt("employer_id"));
                worker.setDepartmentId(rs.getInt("department_id"));
                worker.setPosition(rs.getString("position"));
                worker.setHireDate(rs.getDate("hire_date"));
                worker.setEmployerName(rs.getString("emp_name"));
                worker.setDepartmentName(rs.getString("dept_name"));
                workers.add(worker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }

    public boolean updateWorker(Worker worker) {
        String sql = "UPDATE workers SET name = ?, email = ?, phone = ?, employer_id = ?, " +
                "department_id = ?, position = ?, hire_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, worker.getName());
            pstmt.setString(2, worker.getEmail());
            pstmt.setString(3, worker.getPhone());
            pstmt.setInt(4, worker.getEmployerId());
            pstmt.setInt(5, worker.getDepartmentId());
            pstmt.setString(6, worker.getPosition());
            pstmt.setDate(7, worker.getHireDate());
            pstmt.setInt(8, worker.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteWorker(int id) {
        String sql = "DELETE FROM workers WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
