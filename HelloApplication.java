// Parsa Molahosseini
// Mehrad Bayat
// Jerry-Lee Somera




package com.example.projectv3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import static com.example.projectv3.RegisterController.users;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the login view as the initial scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectv3/login-view.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 800, 600);
        String css = getClass().getResource("/com/example/projectv3/style.css").toExternalForm();
        loginScene.getStylesheets().add(css);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }
    public static void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users.addAll((List<User>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No user data found. Starting fresh.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
