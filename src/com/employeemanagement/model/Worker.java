package com.employeemanagement.model;

import java.sql.Date;

public class Worker {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int employerId;
    private int departmentId;
    private String position;
    private Date hireDate;
    private String employerName; // For display purposes
    private String departmentName; // For display purposes

    public Worker() {
    }

    public Worker(int id, String name, String email, String phone, int employerId,
            int departmentId, String position, Date hireDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.employerId = employerId;
        this.departmentId = departmentId;
        this.position = position;
        this.hireDate = hireDate;
    }

    public Worker(String name, String email, String phone, int employerId,
            int departmentId, String position, Date hireDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.employerId = employerId;
        this.departmentId = departmentId;
        this.position = position;
        this.hireDate = hireDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return name + " - " + position;
    }
}
