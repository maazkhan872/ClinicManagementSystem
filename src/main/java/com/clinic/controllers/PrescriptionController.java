package com.clinic.controllers;

import com.clinic.dao.AppointmentDAO;
import com.clinic.dao.MedicineDAO;
import com.clinic.models.MedicineItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.clinic.dao.PrescriptionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrescriptionController {

    @FXML private ComboBox<String> comboPatient;
    @FXML private TextArea txtSymptoms;
    @FXML private TextArea txtDiagnosis;

    @FXML private ComboBox<String> comboMedicine;
    @FXML private TextField txtQty;
    @FXML private TextField txtDosage;

    @FXML private TableView<MedicineItem> tableItems;
    @FXML private TableColumn<MedicineItem, String> colMedicine;
    @FXML private TableColumn<MedicineItem, Integer> colQty;
    @FXML private TableColumn<MedicineItem, String> colDosage;

    private ObservableList<MedicineItem> medicineList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        MedicineDAO medicineDAO = new MedicineDAO();

        // Load real patients who have appointments
        comboPatient.setItems(
            FXCollections.observableArrayList(
                appointmentDAO.getPatientsWithAppointments()
            )
        );

        // Load medicines from DB
        comboMedicine.setItems(
            FXCollections.observableArrayList(
                medicineDAO.getAllMedicines()
                          .stream()
                          .map(m -> m.getName())
                          .toList()
            )
        );

        // Table setup
        colMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));

        tableItems.setItems(medicineList);
    }
    
    @FXML
    private void addMedicineToList() {
        if (comboMedicine.getValue() == null || txtQty.getText().isEmpty()) {
            showAlert("Error", "Select medicine and enter quantity");
            return;
        }

        MedicineItem item = new MedicineItem(
            comboMedicine.getValue(),
            Integer.parseInt(txtQty.getText()),
            txtDosage.getText()
        );

        medicineList.add(item);

        txtQty.clear();
        txtDosage.clear();
    }
    
    @FXML
    private void handleSavePrescription() {

        if (comboPatient.getValue() == null) {
            showAlert("Error", "Select patient");
            return;
        }

        if (medicineList.isEmpty()) {
            showAlert("Error", "Add at least one medicine");
            return;
        }

        try {

            // patientId extract 
            int patientId = getPatientIdByName(comboPatient.getValue());

            int doctorId = 1; 

            String symptoms = txtSymptoms.getText();
            String diagnosis = txtDiagnosis.getText();

            PrescriptionDAO dao = new PrescriptionDAO();

            // save prescription
            int prescriptionId = dao.savePrescription(
                    patientId,
                    doctorId,
                    symptoms,
                    diagnosis
            );

            if (prescriptionId == -1) {
                showAlert("Error", "Prescription save failed");
                return;
            }

            // save items
            dao.savePrescriptionItems(
                    prescriptionId,
                    medicineList
            );

            showAlert("Success", "Prescription saved to database");

            clearAll();

        } catch (Exception e) {

            e.printStackTrace();

            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    
    private int getPatientIdByName(String name) throws Exception {

        String sql = "SELECT patient_id FROM patients WHERE name=?";

        Connection conn = com.clinic.connection.DatabaseConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        // var rs = ps.executeQuery();

        if (rs.next())
            return rs.getInt("patient_id");

        throw new Exception("Patient not found");
    }

    @FXML
    private void clearAll() {
        comboPatient.getSelectionModel().clearSelection();
        txtSymptoms.clear();
        txtDiagnosis.clear();
        comboMedicine.getSelectionModel().clearSelection();
        txtQty.clear();
        txtDosage.clear();
        medicineList.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

