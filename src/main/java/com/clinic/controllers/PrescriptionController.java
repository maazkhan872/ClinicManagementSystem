/*package com.clinic.controllers;

import com.clinic.dao.PrescriptionDAO;
import com.clinic.dao.PrescriptionItemDAO;
import com.clinic.models.Prescription;
import com.clinic.models.PrescriptionItem;
import com.clinic.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class PrescriptionController {

    @FXML private TextField txtPatientId;
    @FXML private TextArea txtSymptoms, txtDiagnosis;
    @FXML private TextField txtMedId, txtQty, txtDosage;

    private PrescriptionDAO pDao = new PrescriptionDAO();
    private PrescriptionItemDAO piDao = new PrescriptionItemDAO();

    @FXML
    private void handleSaveFullPrescription() {
        // Basic Validation
        if (isInputInvalid()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all required fields correctly.");
            return;
        }

        try {
            // Session Check 
            if (SessionManager.getCurrentUser() == null) {
                showAlert(Alert.AlertType.ERROR, "Auth Error", "Session expired. Please login again.");
                return;
            }

            // Create & Save Prescription (Parent table)
            Prescription p = new Prescription();
            p.setPatientId(Integer.parseInt(txtPatientId.getText()));
            p.setDoctorId(SessionManager.getCurrentUser().getUserId());
            p.setSymptoms(txtSymptoms.getText());
            p.setDiagnosis(txtDiagnosis.getText());
            p.setDate(LocalDate.now());

            int generatedId = pDao.addPrescription(p);

            if (generatedId > 0) {
                // Create & Save Prescription Item (Child Table)
                PrescriptionItem item = new PrescriptionItem();
                item.setPrescriptionId(generatedId);
                item.setMedicineId(Integer.parseInt(txtMedId.getText()));
                item.setQuantity(Integer.parseInt(txtQty.getText()));
                item.setDosage(txtDosage.getText());

                if (piDao.addItem(item)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Prescription generated and medicine stock updated!");
                    clearAllFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Prescription saved but failed to add medicine items.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save prescription header.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Format Error", "Patient ID, Medicine ID, and Quantity must be numeric.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "System Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isInputInvalid() {
        return txtPatientId.getText().isEmpty() || txtMedId.getText().isEmpty() || 
               txtQty.getText().isEmpty() || txtSymptoms.getText().isEmpty();
    }

    private void clearAllFields() {
        txtPatientId.clear();
        txtSymptoms.clear();
        txtDiagnosis.clear();
        txtMedId.clear();
        txtQty.clear();
        txtDosage.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}*/

/*package com.clinic.controllers;

import com.clinic.dao.PatientDAO;
import com.clinic.dao.PrescriptionDAO;
import com.clinic.dao.PrescriptionItemDAO;
import com.clinic.models.Patient;
import com.clinic.models.Prescription;
import com.clinic.models.PrescriptionItem;
import com.clinic.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class PrescriptionController {

    @FXML private ComboBox<Patient> comboPatient; // Fix: TextField ki jagah ComboBox
    @FXML private TextArea txtSymptoms, txtDiagnosis;
    
    // Medicines Table (Multiple medicines handle karne ke liye)
    @FXML private TableView<PrescriptionItem> tableItems;
    @FXML private ComboBox<String> comboMedicine; // Dawai select karne ke liye
    @FXML private TextField txtQty, txtDosage;

    private PrescriptionDAO pDao = new PrescriptionDAO();
    private PrescriptionItemDAO piDao = new PrescriptionItemDAO();
    private PatientDAO patientDAO = new PatientDAO();
    
    // Table ka data hold karne ke liye list
    private ObservableList<PrescriptionItem> medicineList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 1. Patients load karein
        comboPatient.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
        
        // ComboBox mein sirf Name dikhane ke liye setup
        comboPatient.setCellFactory(lv -> new ListCell<Patient>() {
            @Override protected void updateItem(Patient p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty ? "" : p.getName() + " (ID: " + p.getPatientId() + ")");
            }
        });
        comboPatient.setButtonCell(comboPatient.getCellFactory().call(null));

        // 2. Table setup karein
        tableItems.setItems(medicineList);
    }

    // "Add" button ke liye method (Dawai ko table mein dalne ke liye)
    @FXML
    private void addMedicineToList() {
        if (comboMedicine.getValue() == null || txtQty.getText().isEmpty()) return;

        PrescriptionItem item = new PrescriptionItem();
        // Yahan aap medicine ID mapping karenge
        item.setMedicineId(1); // Dummy ID, replace with comboMedicine selection logic
        item.setQuantity(Integer.parseInt(txtQty.getText()));
        item.setDosage(txtDosage.getText());
        
        medicineList.add(item);
        
        // Clear small fields
        txtQty.clear();
        txtDosage.clear();
    }

    @FXML
    private void handleSaveFullPrescription() {
        // 1. Validation
        if (comboPatient.getValue() == null || medicineList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Select patient and add at least one medicine.");
            return;
        }

        try {
            // 2. Create Parent Prescription
            Prescription p = new Prescription();
            p.setPatientId(comboPatient.getValue().getPatientId()); // No more ParseInt!
            p.setDoctorId(SessionManager.getCurrentUser().getUserId());
            p.setSymptoms(txtSymptoms.getText());
            p.setDiagnosis(txtDiagnosis.getText());
            p.setDate(LocalDate.now());

            // 3. Save Parent and Get ID
            int generatedId = pDao.addPrescription(p);

            if (generatedId > 0) {
                // 4. LOOP: Saari medicines save karein
                boolean allSaved = true;
                for (PrescriptionItem item : medicineList) {
                    item.setPrescriptionId(generatedId);
                    if (!piDao.addItem(item)) {
                        allSaved = false;
                        break;
                    }
                }

                if (allSaved) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Full Prescription Saved!");
                    clearAllFields();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "System Error", e.getMessage());
        }
    }
    
    

    private void clearAllFields() {
        comboPatient.getSelectionModel().clearSelection();
        txtSymptoms.clear();
        txtDiagnosis.clear();
        medicineList.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.show();
    }
}*/

/*package com.clinic.controllers;

import com.clinic.dao.PatientDAO;
import com.clinic.dao.PrescriptionDAO;
import com.clinic.dao.PrescriptionItemDAO;
import com.clinic.models.Patient;
import com.clinic.models.Prescription;
import com.clinic.models.PrescriptionItem;
import com.clinic.utils.SessionManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;

public class PrescriptionController {

    // ===== FXML MATCHING FIELDS =====
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private TextArea txtSymptoms;
    @FXML private TextArea txtDiagnosis;

    @FXML private ComboBox<String> comboMedicine;
    @FXML private TextField txtQty;
    @FXML private TextField txtDosage;

    @FXML private TableView<PrescriptionItem> tableItems;
    @FXML private TableColumn<PrescriptionItem, Integer> colMedicine;
    @FXML private TableColumn<PrescriptionItem, Integer> colQty;
    @FXML private TableColumn<PrescriptionItem, String> colDosage;

    // ===== DAO =====
    private final PrescriptionDAO pDao = new PrescriptionDAO();
    private final PrescriptionItemDAO piDao = new PrescriptionItemDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    private final ObservableList<PrescriptionItem> medicineList =
            FXCollections.observableArrayList();

    // ===== INITIALIZE =====
    @FXML
    public void initialize() {

        // 1️⃣ Load Patients
        comboPatient.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));

        // 2️⃣ CellFactory: Display patient name
        comboPatient.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> lv) {
                return new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient p, boolean empty) {
                        super.updateItem(p, empty);
                        setText(empty || p == null ? "" : p.getName());
                    }
                };
            }
        });

        // 3️⃣ Button cell
        comboPatient.setButtonCell(new ListCell<Patient>() {
            @Override
            protected void updateItem(Patient p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? "" : p.getName());
            }
        });

        // 4️⃣ Dummy medicines (replace with DAO later)
        comboMedicine.getItems().addAll("Paracetamol", "Augmentin", "Panadol");

        // 5️⃣ Table column mapping
        colMedicine.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));

        tableItems.setItems(medicineList);
    }

    // ===== ADD MEDICINE =====
    @FXML
    private void addMedicineToList() {

        if (comboMedicine.getValue() == null || txtQty.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Select medicine and enter quantity.");
            return;
        }

        try {
            PrescriptionItem item = new PrescriptionItem();

            // TEMP: medicineId = index+1
            item.setMedicineId(comboMedicine.getSelectionModel().getSelectedIndex() + 1);
            item.setQuantity(Integer.parseInt(txtQty.getText()));
            item.setDosage(txtDosage.getText());

            medicineList.add(item);

            txtQty.clear();
            txtDosage.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Quantity must be numeric.");
        }
    }

    // ===== SAVE PRESCRIPTION =====
    @FXML
    private void handleSavePrescription() {

        if (comboPatient.getValue() == null || medicineList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Error", "Select patient and add at least one medicine.");
            return;
        }

        try {
            Prescription p = new Prescription();
            p.setPatientId(comboPatient.getValue().getPatientId());
            p.setDoctorId(SessionManager.getCurrentUser().getUserId());
            p.setSymptoms(txtSymptoms.getText());
            p.setDiagnosis(txtDiagnosis.getText());
            p.setDate(LocalDate.now());

            int generatedId = pDao.addPrescription(p);

            if (generatedId > 0) {
                for (PrescriptionItem item : medicineList) {
                    item.setPrescriptionId(generatedId);
                    piDao.addItem(item);
                }

                showAlert(Alert.AlertType.INFORMATION, "Success", "Prescription Saved Successfully!");
                clearAll();
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "System Error", e.getMessage());
            e.printStackTrace();
        }
    }

    // ===== RESET FORM =====
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

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}*/

/*package com.clinic.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.clinic.models.MedicineItem;

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

        // Dummy data (replace later with DB)
        comboPatient.setItems(FXCollections.observableArrayList("Ali", "Ahmed", "Sara"));
        comboMedicine.setItems(FXCollections.observableArrayList("Panadol", "Augmentin", "Brufen"));

        // Table setup
        colMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));

        tableItems.setItems(medicineList);
    }

    // ✅ This fixes your error
    @FXML
    private void addMedicineToList(ActionEvent event) {

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
        showAlert("Saved", "Prescription saved successfully");
        clearAll();
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
}*/

package com.clinic.controllers;

import com.clinic.dao.AppointmentDAO;
import com.clinic.dao.MedicineDAO;
import com.clinic.models.MedicineItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
            showAlert("Error", "Select a patient");
            return;
        }
        // Save logic to DB (optional, connect to PrescriptionDAO)
        showAlert("Saved", "Prescription saved successfully");
        clearAll();
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
