package com.employeemanagement.dao;

import com.employeemanagement.model.Employer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployerDAO {

    public boolean addEmployer(Employer employer) {
        String sql = "INSERT INTO employers (name, email, phone, company_name, department_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employer.getName());
            pstmt.setString(2, employer.getEmail());
            pstmt.setString(3, employer.getPhone());
            pstmt.setString(4, employer.getCompanyName());
            pstmt.setInt(5, employer.getDepartmentId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employer> getAllEmployers() {
        List<Employer> employers = new ArrayList<>();
        String sql = "SELECT e.*, d.name as dept_name FROM employers e " +
                "LEFT JOIN departments d ON e.department_id = d.id " +
                "ORDER BY e.name";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employer employer = new Employer();
                employer.setId(rs.getInt("id"));
                employer.setName(rs.getString("name"));
                employer.setEmail(rs.getString("email"));
                employer.setPhone(rs.getString("phone"));
                employer.setCompanyName(rs.getString("company_name"));
                employer.setDepartmentId(rs.getInt("department_id"));
                employer.setDepartmentName(rs.getString("dept_name"));
                employers.add(employer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employers;
    }

    public Employer getEmployerById(int id) {
        String sql = "SELECT e.*, d.name as dept_name FROM employers e " +
                "LEFT JOIN departments d ON e.department_id = d.id " +
                "WHERE e.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Employer employer = new Employer();
                employer.setId(rs.getInt("id"));
                employer.setName(rs.getString("name"));
                employer.setEmail(rs.getString("email"));
                employer.setPhone(rs.getString("phone"));
                employer.setCompanyName(rs.getString("company_name"));
                employer.setDepartmentId(rs.getInt("department_id"));
                employer.setDepartmentName(rs.getString("dept_name"));
                return employer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Employer> getEmployersByDepartment(int departmentId) {
        List<Employer> employers = new ArrayList<>();
        String sql = "SELECT e.*, d.name as dept_name FROM employers e " +
                "LEFT JOIN departments d ON e.department_id = d.id " +
                "WHERE e.department_id = ? ORDER BY e.name";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employer employer = new Employer();
                employer.setId(rs.getInt("id"));
                employer.setName(rs.getString("name"));
                employer.setEmail(rs.getString("email"));
                employer.setPhone(rs.getString("phone"));
                employer.setCompanyName(rs.getString("company_name"));
                employer.setDepartmentId(rs.getInt("department_id"));
                employer.setDepartmentName(rs.getString("dept_name"));
                employers.add(employer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employers;
    }

    public boolean updateEmployer(Employer employer) {
        String sql = "UPDATE employers SET name = ?, email = ?, phone = ?, company_name = ?, department_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employer.getName());
            pstmt.setString(2, employer.getEmail());
            pstmt.setString(3, employer.getPhone());
            pstmt.setString(4, employer.getCompanyName());
            pstmt.setInt(5, employer.getDepartmentId());
            pstmt.setInt(6, employer.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployer(int id) {
        String sql = "DELETE FROM employers WHERE id = ?";

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

    public int getWorkerCount(int employerId) {
        String sql = "SELECT COUNT(*) FROM workers WHERE employer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
