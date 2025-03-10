# JavaFX-EmployeeManager
Employee and Department Management System

A JavaFX-based management application designed to streamline processes related to employee data, department management, and payroll generation.

Overview

This project provides an intuitive user interface and a robust backend to manage employees, departments, and payroll effectively. It supports data persistence using serialization and enables payroll report exports in CSV format.

Features

Employee Management: Add, update, and delete employee records.

Department Management: Create, update, and manage departments dynamically.

Payroll Processing: Generate accurate payroll reports, including gross salary, deductions, and net salary.

Data Persistence: Serialize data to maintain consistency across sessions.

CSV Export: Export payroll data for external usage.

Technologies Used

Java

JavaFX

IntelliJ IDEA

Project Structure

├── src/
│   ├── controllers/
│   └── HelloController.java
│
├── models/
│   ├── Employee.java
│   ├── Department.java
│   └── Payroll.java
│
├── serialization/
│   ├── employees.dat
│   └── departments.dat
│
└── resources/
    └── payroll_reports.csv

## Installation

Clone the repository:

```bash
git clone <repository-url>

Open the project in IntelliJ IDEA and ensure JavaFX is correctly configured.

Usage

Run HelloController.java to launch the application.

Use provided interfaces to manage employee and department data.

Generate payroll and export reports as needed.

Technologies Used

Java

JavaFX

Serialization

Contributing

Feel free to fork the repository, submit issues, and send pull requests for improvements or new features.

License

This project is licensed under the MIT License - see the LICENSE file for details.

