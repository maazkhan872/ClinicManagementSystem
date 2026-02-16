/*package com.clinic.controllers;

import com.clinic.dao.UserDAO;
import com.clinic.models.User;
import com.clinic.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class UserController {

    // Login Fields
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    
    // Registration Fields 
    @FXML private TextField txtNewUsername, txtEmail;
    @FXML private ComboBox<String> comboRole;

    private UserDAO userDAO = new UserDAO();

    
     // Handle User Login
     
    @FXML
    private void handleLogin() {
        String user = txtUsername.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Error", "Please enter both username and password.");
            return;
        }

        User authenticatedUser = userDAO.login(user, pass);

        if (authenticatedUser != null) {
            // Save user info in Session
            SessionManager.setCurrentUser(authenticatedUser);
            
            System.out.println("Login Successful: Welcome " + authenticatedUser.getUsername());
            navigateToDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
        }
    }

    
     // Add New User/Staff
     
    @FXML
    private void handleRegisterUser() {
        if (txtNewUsername.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "All fields are required.");
            return;
        }

        // Mapping model 
        int roleId = comboRole.getSelectionModel().getSelectedIndex() + 1; // Simple logic for role
        User newUser = new User(0, txtNewUsername.getText(), roleId, txtEmail.getText());
        
        // Pass plain password for now as per your DAO
        boolean success = userDAO.registerUser(newUser, "default123"); 

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "New staff user added successfully!");
        }
    }

    private void navigateToDashboard() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/clinic/views/MainDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Clinic Dashboard");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "System Error", "Could not load Dashboard screen.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}*/

package com.clinic.controllers;

import com.clinic.dao.UserDAO;
import com.clinic.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {

    // Registration Fields
    @FXML private TextField txtNewUsername, txtEmail;
    @FXML private ComboBox<String> comboRole;

    // User Table
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> colUserId;
    @FXML private TableColumn<User, String> colUserName;
    @FXML private TableColumn<User, Integer> colUserRole;
    @FXML private TableColumn<User, String> colUserEmail;

    private UserDAO userDAO = new UserDAO();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1️⃣ Populate role combo box
        comboRole.getItems().addAll("Admin", "Doctor", "Receptionist");

        // 2️⃣ Initialize Table Columns
        colUserId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        colUserRole.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("roleId"));
        colUserEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));

        // 3️⃣ Load users into table
        loadUsers();
    }

    @FXML
    private void handleRegisterUser() {
        if (txtNewUsername.getText().isEmpty() || txtEmail.getText().isEmpty() || comboRole.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "All fields are required!");
            return;
        }

        int roleId = comboRole.getSelectionModel().getSelectedIndex() + 1;
        User newUser = new User(0, txtNewUsername.getText(), roleId, txtEmail.getText());

        if (userDAO.registerUser(newUser, "default123")) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "New staff member added!");
            clearFields();
            loadUsers(); // Refresh table
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register user.");
        }
    }

    private void loadUsers() {
        userList.clear();
        userList.addAll(userDAO.getAllUsers()); // Make sure DAO has this method
        userTable.setItems(userList);
    }

    private void clearFields() {
        txtNewUsername.clear();
        txtEmail.clear();
        comboRole.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
