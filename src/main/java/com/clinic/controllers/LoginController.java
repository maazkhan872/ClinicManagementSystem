package com.clinic.controllers;

import com.clinic.dao.UserDAO;
import com.clinic.models.User;
import com.clinic.utils.SessionManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

    private UserDAO userDAO = new UserDAO();

    // LOGIN HANDLER 
    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            return;
        }

        // Authenticate user 
        User user = userDAO.login(username, password);

        if (user != null) {
            // Store session
            SessionManager.setCurrentUser(user);

            // Navigate to Dashboard
            navigateToDashboard();

        } else {
            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
        }
    }

    // DASHBOARD NAVIGATION 
    private void navigateToDashboard() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();

            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/clinic/views/MainDashboard.fxml")
            );

            stage.setScene(new Scene(root));
            stage.setTitle("Clinic Dashboard");
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CANCEL 
    @FXML
    private void handleCancel() {
        System.exit(0);
    }
}