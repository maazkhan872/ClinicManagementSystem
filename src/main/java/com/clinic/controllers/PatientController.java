/*package com.clinic.controllers;

import com.clinic.dao.PatientDAO;
import com.clinic.models.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController {

    @FXML private TextField txtName, txtAge, txtPhone;
    @FXML private TextArea txtAddress;
    @FXML private ComboBox<String> comboGender;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> colId;
    @FXML private TableColumn<Patient, String> colName, colPhone;

    private PatientDAO patientDAO = new PatientDAO();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Table Columns Setup
        colId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        // Gender ComboBox Items
        comboGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        
        loadPatientData();
    }

    private void loadPatientData() {
        patientList.clear();
        patientList.addAll(patientDAO.getAllPatients()); // Make sure getAllPatients exists in DAO
        patientTable.setItems(patientList);
    }

    @FXML
    private void handleSavePatient() {
        if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty()) {
            showAlert("Error", "Name and Phone are required!", Alert.AlertType.ERROR);
            return;
        }

        Patient p = new Patient();
        p.setName(txtName.getText());
        p.setAge(Integer.parseInt(txtAge.getText()));
        p.setGender(comboGender.getValue());
        p.setPhone(txtPhone.getText());
        p.setAddress(txtAddress.getText());

        if (patientDAO.addPatient(p)) {
            showAlert("Success", "Patient Registered Successfully!", Alert.AlertType.INFORMATION);
            clearFields();
            loadPatientData();
        }
    }
    
    @FXML
    private void clearAll() { }


    @FXML
    private void clearFields() {
        txtName.clear(); txtAge.clear(); txtPhone.clear(); txtAddress.clear();
        comboGender.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}*/

package com.clinic.controllers;

import com.clinic.dao.PatientDAO;
import com.clinic.models.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController {

    @FXML private TextField txtName, txtAge, txtPhone;
    @FXML private TextArea txtAddress;
    @FXML private ComboBox<String> comboGender;
    @FXML private DatePicker pickDOB; // ✅ Added
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> colId;
    @FXML private TableColumn<Patient, String> colName, colPhone;

    private PatientDAO patientDAO = new PatientDAO();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Table Columns Setup
        colId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Gender ComboBox Items
        comboGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Load Data
        loadPatientData();
    }

    private void loadPatientData() {
        patientList.clear();
        patientList.addAll(patientDAO.getAllPatients());
        patientTable.setItems(patientList);
    }

    @FXML
    private void handleSavePatient() {
        if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty()) {
            showAlert("Error", "Name and Phone are required!", Alert.AlertType.ERROR);
            return;
        }

        Patient p = new Patient();
        p.setName(txtName.getText());
        p.setAge(Integer.parseInt(txtAge.getText()));
        p.setGender(comboGender.getValue());
        p.setPhone(txtPhone.getText());
        p.setAddress(txtAddress.getText());
        p.setDob(pickDOB.getValue()); // ✅ Set DOB

        if (patientDAO.addPatient(p)) {
            showAlert("Success", "Patient Registered Successfully!", Alert.AlertType.INFORMATION);
            clearFields();
            loadPatientData();
        } else {
            showAlert("Error", "Failed to save patient. Try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear(); 
        txtAge.clear(); 
        txtPhone.clear(); 
        txtAddress.clear();
        comboGender.getSelectionModel().clearSelection();
        pickDOB.setValue(null); // ✅ Clear DOB
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
