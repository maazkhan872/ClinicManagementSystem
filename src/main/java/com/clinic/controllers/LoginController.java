/*package com.clinic.controllers;

import com.clinic.dao.UserDAO;
import com.clinic.models.User;
import com.clinic.utils.SessionManager;
import javafx.event.ActionEvent;
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

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password!");
            return;
        }

        // Check from Database
        User user = userDAO.validateLogin(username, password);

        if (user != null) {
            // Store user in Session
            SessionManager.setCurrentUser(user);

            // Load Dashboard
            try {
                loadDashboard();
            } catch (IOException e) {
                lblMessage.setText("Error loading dashboard!");
                e.printStackTrace();
            }
        } else {
            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
        }
    }

    private void loadDashboard() throws IOException {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/clinic/views/MainDashboard.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Clinic Management System - Dashboard");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        System.exit(0);
    }
}*/


/*package com.clinic.controllers;

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

    @FXML
    private void handleLogin() { // FXML mein onAction="#handleLogin" hona chahiye
        String user = txtUsername.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            return;
        }

        // Validate from Database
        User authenticatedUser = userDAO.validateLogin(user, pass);

        if (authenticatedUser != null) {
            SessionManager.setCurrentUser(authenticatedUser);
            navigateToDashboard();
        } else {
            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
        }
    }

    private void navigateToDashboard() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            // Path check: resources folder ke andar se
            Parent root = FXMLLoader.load(getClass().getResource("/com/clinic/views/MainDashboard.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Clinic Dashboard");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        System.exit(0);
    }
}*/

/*
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

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            return;
        }

        User authenticatedUser = userDAO.validateLogin(username, password);

        if (authenticatedUser != null) {
            SessionManager.setCurrentUser(authenticatedUser);
            navigateToDashboard();
        } else {
            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
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
        }
    }

    @FXML
    private void handleCancel() {
        System.exit(0);
    }
}
*/

/*
package com.clinic.controllers;

import com.clinic.dao.UserDAO;
import com.clinic.models.User;
import com.clinic.utils.SessionManager;  // ✅ old technique

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

    // ----------------- LOGIN HANDLER -----------------
    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        // ✅ Validation
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            return;
        }

        // ✅ Authenticate user
        User user = userDAO.login(username, password);

        if (user != null) {

            // ✅ Store session using old SessionManager
            SessionManager.setCurrentUser(user);

            // ✅ Navigate to Dashboard
            navigateToDashboard();

        } else {

            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
        }
    }

    // ----------------- DASHBOARD NAVIGATION -----------------
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

    // ----------------- CANCEL / EXIT -----------------
    @FXML
    private void handleCancel() {
        System.exit(0);
    }
} */

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

    // ----------------- LOGIN HANDLER -----------------
    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        // ✅ Validation
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            return;
        }

        // ✅ Authenticate user (plain old + hashed new)
        User user = userDAO.login(username, password);

        if (user != null) {
            // ✅ Store session
            SessionManager.setCurrentUser(user);

            // ✅ Navigate to Dashboard
            navigateToDashboard();

        } else {
            lblMessage.setText("Invalid credentials. Try again.");
            txtPassword.clear();
        }
    }

    // ----------------- DASHBOARD NAVIGATION -----------------
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

    // ----------------- CANCEL / EXIT -----------------
    @FXML
    private void handleCancel() {
        System.exit(0);
    }
}