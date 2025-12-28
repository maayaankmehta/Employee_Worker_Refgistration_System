package com.employeemanagement.model;

public class Employer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String companyName;
    private int departmentId;
    private String departmentName; // For display purposes

    public Employer() {
    }

    public Employer(int id, String name, String email, String phone, String companyName, int departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.departmentId = departmentId;
    }

    public Employer(String name, String email, String phone, String companyName, int departmentId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.departmentId = departmentId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return name + " (" + companyName + ")";
    }
}
