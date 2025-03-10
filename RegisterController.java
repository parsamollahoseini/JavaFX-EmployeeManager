// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera


package com.example.projectv3;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label errorLabel;

    public static final List<User> users = new ArrayList<>(); // Temporary storage; replace with file-based storage for persistence

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "User"));
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            errorLabel.setText("Please fill in all fields.");
            errorLabel.setVisible(true);
            return;
        }

        if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
            errorLabel.setText("Username already exists.");
            errorLabel.setVisible(true);
            return;
        }

        // Add new user
        users.add(new User(username, password, role));
        saveUsersToFile();

        errorLabel.setText("Registration successful!");
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setVisible(true);
    }


    public static List<User> getUsers() {
        return users;
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
