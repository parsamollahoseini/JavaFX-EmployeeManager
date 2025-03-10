// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera


package com.example.projectv3;

public class Payroll {
    private Employee employee;
    private double hoursWorked;
    private double overtimeHours;
    private double bonus;
    private double grossSalary;
    private double taxDeduction;
    private double netSalary;

    public Payroll(Employee employee, double hoursWorked, double overtimeHours, double bonus) {
        this.employee = employee;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = overtimeHours;
        this.bonus = bonus;
        calculate();
    }

    private void calculate() {
        double overtimePay = employee.getHourlyRate() * 1.5 * overtimeHours;
        grossSalary = (employee.getHourlyRate() * hoursWorked) + overtimePay + bonus;
        taxDeduction = calculateTax(grossSalary);
        netSalary = grossSalary - taxDeduction;
    }

    private double calculateTax(double grossSalary) {
        if (grossSalary <= 3000) return grossSalary * 0.10;
        else if (grossSalary <= 6000) return grossSalary * 0.15;
        else return grossSalary * 0.20;
    }

    public Employee getEmployee() {
        return employee;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getTaxDeduction() {
        return taxDeduction;
    }

    public double getNetSalary() {
        return netSalary;
    }
}
