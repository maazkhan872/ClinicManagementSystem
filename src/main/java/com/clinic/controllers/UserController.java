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
        // Populate role combo box
        comboRole.getItems().addAll("Admin", "Doctor", "Receptionist");

        // Initialize Table Columns
        colUserId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        colUserRole.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("roleId"));
        colUserEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));

        // Load users into table
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
        userList.addAll(userDAO.getAllUsers()); 
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
