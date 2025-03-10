// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera

package com.example.projectv3;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private TextField deptIdField, deptNameField;
    @FXML private TableView<Department> departmentTable;
    @FXML private TableColumn<Department, Integer> deptIdColumn;
    @FXML private TableColumn<Department, String> deptNameColumn;

    private ObservableList<Department> departments = FXCollections.observableArrayList();

    @FXML private TextField idField, nameField, depteld, positionField, emailField, phoneField, rateField, attendanceField, leaveField;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn, deptColumn, positionColumn, emailColumn, phoneColumn;
    @FXML private TableColumn<Employee, Double> rateColumn;

    @FXML private TextField hoursField, overtimeField, bonusField;
    @FXML private ComboBox<Employee> employeeDropdown;
    @FXML private Label resultLabel;

    @FXML private TableView<Payroll> reportTable;
    @FXML private TableColumn<Payroll, String> reportNameColumn;
    @FXML private TableColumn<Payroll, Double> reportGrossColumn;
    @FXML private TableColumn<Payroll, Double> reportTaxColumn;
    @FXML private TableColumn<Payroll, Double> reportNetColumn;

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();
    private final ObservableList<Payroll> payrolls = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    public void initialize() {
        // Bind Employee Table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));

        deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        deptNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentTable.setItems(departments);

        employeeTable.setItems(employees);
        employeeDropdown.setItems(employees);

        departmentComboBox.setItems(departments);
        departmentComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department department) {
                return department != null ? department.getName() : "";
            }

            @Override
            public Department fromString(String string) {
                return departments.stream()
                        .filter(dept -> dept.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        if ("Employee".equals(userRole)) {
            // Disable admin-only features
            departmentTable.setDisable(true);
            departmentComboBox.setDisable(true);
        }


        reportNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployee().getName()));
        reportGrossColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getGrossSalary()).asObject());
        reportTaxColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTaxDeduction()).asObject());
        reportNetColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getNetSalary()).asObject());
        reportTable.setItems(payrolls);
    }

    // **Export Report**
    @FXML
    public void exportReport() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Payroll Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("Name,Gross Salary,Tax Deduction,Net Salary\n");
                    for (Payroll payroll : payrolls) {
                        writer.write(String.format("%s,%.2f,%.2f,%.2f\n",
                                payroll.getEmployee().getName(),
                                payroll.getGrossSalary(),
                                payroll.getTaxDeduction(),
                                payroll.getNetSalary()));
                    }
                    showAlert("Success", "Report exported successfully.");
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to export report: " + e.getMessage());
        }
    }

    public void generatePayroll() {
        try {
            Employee selectedEmployee = employeeDropdown.getValue();
            if (selectedEmployee != null) {
                double hoursWorked = Double.parseDouble(hoursField.getText());
                double overtimeHours = Double.parseDouble(overtimeField.getText());
                double bonus = Double.parseDouble(bonusField.getText());

                Payroll payroll = new Payroll(selectedEmployee, hoursWorked, overtimeHours, bonus);
                payrolls.add(payroll);

                // Update Report Table
                reportTable.setItems(payrolls);

                showAlert("Success", "Payroll generated successfully.");
            } else {
                showAlert("Error", "Please select an employee.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid numeric values entered for payroll.");
        }
    }

    // Employee Management

    public void addEmployee() {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
                    departmentComboBox.getValue() == null || positionField.getText().isEmpty() ||
                    emailField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                    rateField.getText().isEmpty() || attendanceField.getText().isEmpty() ||
                    leaveField.getText().isEmpty()) {
                showAlert("Missing Input", "All fields are required. Please fill out every field.");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            if (employees.stream().anyMatch(emp -> emp.getId() == id)) {
                showAlert("Duplicate ID", "An employee with this ID already exists.");
                return;
            }

            String name = nameField.getText();
            String department = departmentComboBox.getValue().getName(); // Fetching the department name
            String position = positionField.getText();
            String email = emailField.getText();
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                showAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }

            String phone = phoneField.getText();
            if (!phone.matches("\\d{10}")) {
                showAlert("Invalid Phone", "Please enter a valid 10-digit phone number.");
                return;
            }

            double hourlyRate = Double.parseDouble(rateField.getText());
            int attendanceDays = Integer.parseInt(attendanceField.getText());
            int leaveDays = Integer.parseInt(leaveField.getText());
            if (hourlyRate < 0 || attendanceDays < 0 || leaveDays < 0) {
                showAlert("Invalid Input", "Rate, Attendance, and Leave days cannot be negative.");
                return;
            }

            employees.add(new Employee(id, name, department, position, email, phone, hourlyRate, attendanceDays, leaveDays));
            clearEmployeeFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numeric values for rate, attendance, and leave days.");
        }
    }


    @FXML
    public void deleteEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employees.remove(selected);
        } else {
            showAlert("No Selection", "Please select an employee to delete.");
        }
    }

    // **Employee Selection**
    @FXML
    public void onEmployeeSelected() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idField.setText(String.valueOf(selected.getId()));
            nameField.setText(selected.getName());
            positionField.setText(selected.getPosition());
            emailField.setText(selected.getEmail());
            phoneField.setText(selected.getPhone());
            rateField.setText(String.valueOf(selected.getHourlyRate()));
            attendanceField.setText(String.valueOf(selected.getAttendanceDays()));
            leaveField.setText(String.valueOf(selected.getLeaveDays()));
        }
    }

    @FXML
    public void updateEmployee() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (nameField.getText().isEmpty() || departmentComboBox.getValue() == null ||
                    positionField.getText().isEmpty() || emailField.getText().isEmpty() ||
                    phoneField.getText().isEmpty() || rateField.getText().isEmpty() ||
                    attendanceField.getText().isEmpty() || leaveField.getText().isEmpty()) {
                showAlert("Invalid Input", "Please fill out all fields before updating.");
                return;
            }

            try {
                selected.setName(nameField.getText());
                selected.setDepartment(departmentComboBox.getValue().getName());
                selected.setPosition(positionField.getText());
                selected.setEmail(emailField.getText());
                selected.setPhone(phoneField.getText());
                selected.setHourlyRate(Double.parseDouble(rateField.getText()));
                selected.setAttendanceDays(Integer.parseInt(attendanceField.getText()));
                selected.setLeaveDays(Integer.parseInt(leaveField.getText()));

                employeeTable.refresh(); // Refresh the table to reflect the changes
                clearEmployeeFields();

            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numeric values for Hourly Rate, Attendance Days, and Leave Days.");
            }
        } else {
            showAlert("No Selection", "Please select an employee to update.");
        }
    }

    @FXML
    public void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.dat"))) {
            oos.writeObject(new ArrayList<>(employees));
            showAlert("Success", "Employees saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to save employees: " + e.getMessage());
        }
    }

    @FXML
    public void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employees.dat"))) {
            employees.clear();
            employees.addAll((ArrayList<Employee>) ois.readObject());
            showAlert("Success", "Employees loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load employees: " + e.getMessage());
        }
    }

    // Department Management
    @FXML
    public void addDepartment() {
        try {
            int id = Integer.parseInt(deptIdField.getText());
            String name = deptNameField.getText();
            // Optionally add manager field if needed
            departments.add(new Department(id, name));

            // Clear input fields
            deptIdField.clear();
            deptNameField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid department ID and name.");
        }
    }


    @FXML
    public void deleteDepartment() {
        Department selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            departments.remove(selected);
        } else {
            showAlert("No Selection", "Please select a department to delete.");
        }
    }

    @FXML
    public void updateDepartment() {
        Department selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (deptNameField.getText().isEmpty()) {
                showAlert("Invalid Input", "Please enter a valid department name.");
                return;
            }

            try {
                String oldDepartmentName = selected.getName();
                String newDepartmentName = deptNameField.getText();

                // Update the department name
                selected.setName(newDepartmentName);

                // Update all employees who belong to this department
                for (Employee employee : employees) {
                    if (employee.getDepartment().equals(oldDepartmentName)) {
                        employee.setDepartment(newDepartmentName);
                    }
                }

                departmentTable.refresh(); // Refresh the department table
                employeeTable.refresh(); // Refresh the employee table

                // Clear the department input fields
                clearDepartmentFields();

                showAlert("Success", "Department updated successfully.");
            } catch (Exception e) {
                showAlert("Error", "Failed to update department: " + e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a department to update.");
        }
    }

    @FXML
    public void saveDepartments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("departments.dat"))) {
            oos.writeObject(new ArrayList<>(departments));
            showAlert("Success", "Departments saved successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to save departments: " + e.getMessage());
        }
    }
    @FXML
    public void loadDepartments() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("departments.dat"))) {
            departments.clear();
            List<Department> loadedDepartments = (List<Department>) ois.readObject();
            departments.addAll(loadedDepartments);
            showAlert("Success", "Departments loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load departments: " + e.getMessage());
        }
    }


    // Utility Methods
    private void clearEmployeeFields() {
        idField.clear();
        nameField.clear();
        departmentComboBox.getSelectionModel().clearSelection();
        positionField.clear();
        emailField.clear();
        phoneField.clear();
        rateField.clear();
        attendanceField.clear();
        leaveField.clear();
    }

    private void clearDepartmentFields() {
        deptIdField.clear();
        deptNameField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static String userRole;

    public void setUserRole(String role) {
        // Set up the interface or access controls based on the role
        System.out.println("User role: " + role);
    }


    }
