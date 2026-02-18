/*package com.clinic.controllers;

import com.clinic.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class DashboardController {

    // ✅ Buttons must match FXML fx:id exactly
    @FXML private Button btnHome;
    @FXML private Button btnPatients;
    @FXML private Button btnAppointments;
    @FXML private Button btnPrescriptions;
    @FXML private Button btnBilling;
    @FXML private Button btnUsers;
    @FXML private Button btnInventory;

    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        // Hide buttons based on role
        if (SessionManager.getCurrentUser() != null) {
            int roleId = SessionManager.getCurrentUser().getRoleId();
            if (roleId != 1) { // Non-admin
                if (btnUsers != null) btnUsers.setVisible(false);
                if (btnInventory != null) btnInventory.setVisible(false);
                if (btnBilling != null) btnBilling.setVisible(false);
            }
        }
    }

    // Button Actions
    @FXML
    private void showHome() {
        contentArea.getChildren().clear();
        // Uncomment if you have a Home.fxml
        // loadView("Home.fxml");
    }

    @FXML
    private void showPatients() {
        loadView("PatientModule.fxml");
    }

    @FXML
    private void showAppointments() {
        loadView("AppointmentModule.fxml");
    }

    @FXML
    private void showPrescriptions() {
        loadView("Prescription.fxml");
    }

    @FXML
    private void showBilling() {
        loadView("Billing.fxml");
    }

    @FXML
    private void showUsers() {
        loadView("UserManagement.fxml");
    }

    @FXML
    private void handleLogout() {
        SessionManager.cleanUserSession();
    }

    // Generic method to load FXML into the center StackPane
    private void loadView(String fxmlFile) {
        try {
            String path = "/com/clinic/views/" + fxmlFile;
            java.net.URL resource = getClass().getResource(path);

            if (resource == null) {
                throw new IOException("FXML file not found at: " + path);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            if (contentArea != null) {
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            }

        } catch (IOException e) {
            showError("Navigation Error", "Could not load " + fxmlFile);
            e.printStackTrace();
        }
    }
    
    private void loadView(String fxmlFile) {

        try {

            String path = "/com/clinic/views/" + fxmlFile;   // ✅ FIXED HERE

            java.net.URL resource = getClass().getResource(path);

            if (resource == null) {

                throw new IOException("FXML file not found at: " + path);

            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            contentArea.getChildren().clear();

            contentArea.getChildren().add(root);

        }

        catch (IOException e) {

            showError("Navigation Error", "Could not load " + fxmlFile);

            e.printStackTrace();

        }

    }


    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
*/
/*
package com.clinic.controllers;

import com.clinic.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class DashboardController {

    @FXML private Button btnHome;
    @FXML private Button btnPatients;
    @FXML private Button btnAppointments;
    @FXML private Button btnPrescriptions;
    @FXML private Button btnBilling;
    @FXML private Button btnUsers;
    @FXML private Button btnInventory;

    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        // Hide buttons based on role
        if (SessionManager.getCurrentUser() != null) {
            int roleId = SessionManager.getCurrentUser().getRoleId();
            if (roleId != 1) { // Non-admin
                if (btnUsers != null) btnUsers.setVisible(false);
                if (btnInventory != null) btnInventory.setVisible(false);
                if (btnBilling != null) btnBilling.setVisible(false);
            }
        }

        // Show default dashboard content on load
        showHome(null);
    }

    // ----------------- BUTTON ACTIONS -----------------
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
    private void showPatients (ActionEvent event) {
        loadView("PatientModule.fxml");
    }

    @FXML
    private void showAppointments(ActionEvent event) {
        loadView("AppointmentModule.fxml");
    }

    
    @FXML
    private void showAppointments(ActionEvent event) {
        loadPlaceholder("Appointments Module");
    }

    @FXML
    private void showPrescriptions(ActionEvent event) {
        loadPlaceholder("Prescriptions Module");
    }

    @FXML
    private void showBilling(ActionEvent event) {
        loadPlaceholder("Billing & Invoices Module");
    }

    @FXML
    private void showUsers(ActionEvent event) {
        loadPlaceholder("User Management Module");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.cleanUserSession();
    }

    // ----------------- GENERIC LOAD METHODS -----------------
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

    // Placeholder for modules without FXML yet
    private void loadPlaceholder(String moduleName) {
        contentArea.getChildren().clear();
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Text text = new Text(moduleName + " - Content will appear here");
        text.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        box.getChildren().add(text);
        contentArea.getChildren().add(box);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} */


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

import java.io.IOException;

public class DashboardController {

    // ----------------- BUTTONS -----------------
    @FXML private Button btnHome;
    @FXML private Button btnPatients;
    @FXML private Button btnAppointments;
    @FXML private Button btnPrescriptions;
    @FXML private Button btnBilling;
    @FXML private Button btnUsers;
    @FXML private Button btnInventory;

    @FXML private StackPane contentArea;

    // ----------------- INITIALIZE -----------------
    @FXML
    public void initialize() {
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

    // ----------------- BUTTON ACTIONS -----------------
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
    private void showPatients(ActionEvent event) {
        loadView("PatientModule.fxml");
    }

    @FXML
    private void showAppointments(ActionEvent event) {
        loadView("AppointmentModule.fxml");
    }

    @FXML
    private void showPrescriptions(ActionEvent event) {
        loadView("Prescription.fxml");
    }

    @FXML
    private void showBilling(ActionEvent event) {
        loadView("Billing.fxml");
    }

    @FXML
    private void showUsers(ActionEvent event) {
        loadView("UserManagement.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.cleanUserSession();
    }

    // ----------------- GENERIC FXML LOAD -----------------
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

    // ----------------- PLACEHOLDER (Optional) -----------------
    private void loadPlaceholder(String moduleName) {
        contentArea.getChildren().clear();
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);

        Text text = new Text(moduleName + " - Content will appear here");
        text.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        box.getChildren().add(text);
        contentArea.getChildren().add(box);
    }

    // ----------------- ERROR ALERT -----------------
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

