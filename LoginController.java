package com.example.projectv3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.projectv3.HelloApplication.loadUsersFromFile;
import static com.example.projectv3.RegisterController.users;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;


    public void initialize() {
        loadUsersFromFile();
    }

    private static final String ADMIN_ROLE = "Admin";
    private static final String EMPLOYEE_ROLE = "Employee";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                navigateToMain(user.getRole());
                return;
            }
        }

        errorLabel.setText("Invalid username or password.");
        errorLabel.setVisible(true);
    }


    private void navigateToMain(String role) {
        try {
            // Load the main view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectv3/hello-view.fxml"));
            Scene mainScene = new Scene(fxmlLoader.load(), 800,600);
            String css = getClass().getResource("/com/example/projectv3/style.css").toExternalForm();
            mainScene.getStylesheets().add(css);
            // Pass the role to the main controller (if needed)
            HelloController controller = fxmlLoader.getController();
            controller.setUserRole(role); // Create setUserRole method in HelloController

            // Get the current stage and set the new scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("HR Management System");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectv3/register-view.fxml"));
            Scene registerScene = new Scene(loader.load(), 800,600);
            String css = getClass().getResource("/com/example/projectv3/style.css").toExternalForm();
            registerScene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(registerScene);
            stage.showAndWait(); // Wait for the registration window to close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
