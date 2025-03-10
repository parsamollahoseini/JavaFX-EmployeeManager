// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera



package com.example.projectv3;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String department;
    private String position;
    private String email;
    private String phone;
    private double hourlyRate;
    private int attendanceDays;
    private int leaveDays;

    public Employee(int id, String name, String department, String position, String email, String phone, double hourlyRate, int attendanceDays, int leaveDays) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.hourlyRate = hourlyRate;
        this.attendanceDays = attendanceDays;
        this.leaveDays = leaveDays;
    }

    // Getters and Setters
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(int attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}
