package com.clinic.controllers;

import com.clinic.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class DashboardController {

    // BUTTONS 
    @FXML private Button btnHome;
    @FXML private Button btnPatients;
    @FXML private Button btnAppointments;
    @FXML private Button btnPrescriptions;
    @FXML private Button btnBilling;
    @FXML private Button btnUsers;
    @FXML private Button btnInventory;
    @FXML private Button btnLogout; 

    @FXML private StackPane contentArea;

    // INITIALIZE
    @FXML
    public void initialize() {
    	
        // Protect Dashboard
        if (!SessionManager.isLoggedIn()) {
            showAlert("Unauthorized access. Please login first.");
            redirectToLogin();
            return;
        }

        // Hide buttons based on role
        if (SessionManager.getCurrentUser() != null) {
            int roleId = SessionManager.getCurrentUser().getRoleId();
            if (roleId != 1) { // Non-admin
                if (btnUsers != null) btnUsers.setVisible(false);
                if (btnInventory != null) btnInventory.setVisible(false);
                if (btnBilling != null) btnBilling.setVisible(false);
            }
        }

        // Show default home content
        showHome(null);
    }

    // BUTTON ACTIONS 
    @FXML
    private void showHome(ActionEvent event) {
        contentArea.getChildren().clear();

        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);

        Text title = new Text("Welcome to Clinic Management System");
        title.getStyleClass().add("welcome-text");

        Text subtitle = new Text("Select an option from the sidebar to get started.");
        subtitle.getStyleClass().add("subtitle-text");

        welcomeBox.getChildren().addAll(title, subtitle);
        contentArea.getChildren().add(welcomeBox);
    }

    @FXML
    private void showPatients(ActionEvent event) { loadView("PatientModule.fxml"); }

    @FXML
    private void showAppointments(ActionEvent event) { loadView("AppointmentModule.fxml"); }

    @FXML
    private void showPrescriptions(ActionEvent event) { loadView("Prescription.fxml"); }

    @FXML
    private void showBilling(ActionEvent event) { loadView("Billing.fxml"); }

    @FXML
    private void showUsers(ActionEvent event) { loadView("UserManagement.fxml"); }

    // Logout
    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.logout(); // Clear session
        redirectToLogin();
    }

    // GENERIC FXML LOAD 
    private void loadView(String fxmlFile) {
        try {
            String path = "/com/clinic/views/" + fxmlFile;
            java.net.URL resource = getClass().getResource(path);

            if (resource == null) {
                throw new IOException("FXML file not found at: " + path);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);

        } catch (IOException e) {
            showError("Navigation Error", "Could not load " + fxmlFile);
            e.printStackTrace();
        }
    }

    //  ALERT 
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Access Denied");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // REDIRECT TO LOGIN 
    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/clinic/views/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) contentArea.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Clinic Management System - Login");

        } catch (IOException e) {
            e.printStackTrace();
            showError("Navigation Error", "Could not load Login screen");
        }
    }
}
