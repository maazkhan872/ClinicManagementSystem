/*package com.clinic.controllers;

import com.clinic.dao.AppointmentDAO;
import com.clinic.dao.PatientDAO;
import com.clinic.models.Appointment;
import com.clinic.models.Patient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentController {

    @FXML private ComboBox<Patient> comboPatient;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox<String> comboTimeSlot;
    @FXML private TextArea txtReason;
    @FXML private TabPane appointmentTabs;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
    	
    	comboPatient.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
        
        // FIX: Taake ID ki jagah Patient ka Name dikhe
        comboPatient.setCellFactory(lv -> new ListCell<Patient>() {
            @Override protected void updateItem(Patient p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty ? "" : p.getName());
            }
        });
        comboPatient.setButtonCell(comboPatient.getCellFactory().call(null));

        // Time slots (already correct)
        comboTimeSlot.setItems(FXCollections.observableArrayList("09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00"));
        
        comboPatient.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
        
        // Professional Time Slots
        comboTimeSlot.setItems(FXCollections.observableArrayList(
            "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00"
        ));
    }

    @FXML
    private void handleSchedule() {
        // 1. Validation
        if (comboPatient.getValue() == null || pickDate.getValue() == null || comboTimeSlot.getValue() == null) {
            showAlert("Input Error", "Please fill all required fields!", Alert.AlertType.WARNING);
            return;
        }

        // Data Preparation
        Patient selectedPatient = comboPatient.getValue();
        LocalDate date = pickDate.getValue();
        LocalTime time = LocalTime.parse(comboTimeSlot.getValue()); // String "09:00" to LocalTime

        // Object Creation 
        Appointment appt = new Appointment();
        appt.setPatientId(selectedPatient.getPatientId());
        appt.setDoctorId(1); // Default/Current Doctor ID
        appt.setAppDate(date);
        appt.setAppTime(time);
        appt.setStatus("Scheduled");
        appt.setReason(txtReason.getText());

        // Call DAO 
        if (appointmentDAO.bookAppointment(appt)) {
            showAlert("Success", "Appointment booked successfully!", Alert.AlertType.INFORMATION);
            clearFields();
            appointmentTabs.getSelectionModel().select(0); // Switch to list view
        } else {
            showAlert("Conflict/Error", "Could not book appointment. Doctor might be busy.", Alert.AlertType.ERROR);
        }
        
    }

    private void clearFields() {
        comboPatient.getSelectionModel().clearSelection();
        pickDate.setValue(null);
        comboTimeSlot.getSelectionModel().clearSelection();
        txtReason.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void switchToFormTab() {
        appointmentTabs.getSelectionModel().select(1);
    }
    
    
    
}*/

/*package com.clinic.controllers;

import com.clinic.dao.AppointmentDAO;
import com.clinic.models.Appointment;
import com.clinic.models.Patient;
import com.clinic.models.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;

public class AppointmentController {

    // FXML Components
    @FXML private DatePicker dateFilter;
    @FXML private ComboBox<String> comboStatusFilter;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colPatient;
    @FXML private TableColumn<Appointment, String> colDoctor;
    @FXML private TableColumn<Appointment, String> colReason;
    @FXML private TableColumn<Appointment, String> colStatus;

   

    
    // New Appointment Form
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private ComboBox<Doctor> comboDoctor;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox<String> comboTimeSlot;
    @FXML private TextArea txtReason;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup Table Columns
        colTime.setCellValueFactory(param -> param.getValue().timeStringProperty());
        colPatient.setCellValueFactory(param -> param.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(param -> param.getValue().doctorNameProperty());
        colReason.setCellValueFactory(param -> param.getValue().reasonProperty());
        colStatus.setCellValueFactory(param -> param.getValue().statusProperty());

        // Setup Status Filter
        comboStatusFilter.getItems().addAll("All", "Scheduled", "Completed", "Cancelled");
        comboStatusFilter.getSelectionModel().selectFirst();

        // Load Data
        loadAppointments(LocalDate.now(), "All");
    }

    // Load appointments from DAO with optional filtering
    private void loadAppointments(LocalDate date, String status) {
        List<Appointment> data = appointmentDAO.getAppointmentsByDate(date);

        // Apply status filter
        if (status != null && !status.equals("All")) {
            data = data.stream()
                       .filter(a -> a.getStatus().equalsIgnoreCase(status))
                       .collect(Collectors.toList());
        }

        appointmentList.setAll(data);
        appointmentTable.setItems(appointmentList);
    }

    // Called when date picker changes
    @FXML
    private void filterByDate() {
        LocalDate selectedDate = dateFilter.getValue();
        String status = comboStatusFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        loadAppointments(selectedDate, status);
    }

    // Called when "Filter" button clicked
    @FXML
    private void applyFilters() {
        LocalDate selectedDate = dateFilter.getValue();
        String status = comboStatusFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        if (status == null) status = "All";
        loadAppointments(selectedDate, status);
    }

    // Reset new appointment form
    @FXML
    private void resetForm() {
        comboPatient.getSelectionModel().clearSelection();
        comboDoctor.getSelectionModel().clearSelection();
        pickDate.setValue(null);
        comboTimeSlot.getSelectionModel().clearSelection();
        txtReason.clear();
    }

    // Handle scheduling a new appointment
    @FXML
    private void handleSchedule() {
        if (comboPatient.getValue() == null || comboDoctor.getValue() == null
            || pickDate.getValue() == null || comboTimeSlot.getValue() == null
            || txtReason.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields to schedule an appointment.");
            return;
        }

        Appointment newApp = new Appointment();
        newApp.setPatientId(comboPatient.getValue().getPatientId()); // set ID
        newApp.setPatientName(comboPatient.getValue().getName());    // optional: display name
        newApp.setDoctorId(comboDoctor.getValue().getDoctorId());
        newApp.setDoctorName(comboDoctor.getValue().getName());
        newApp.setAppDate(pickDate.getValue());
    //    newApp.setAppTime(comboTimeSlot.getValue());
        newApp.setReason(txtReason.getText());
        newApp.setStatus("Scheduled");
        
        // Set date and time
        newApp.setAppDate(pickDate.getValue());
        String timeString = comboTimeSlot.getValue();
        if (timeString != null && !timeString.isEmpty()) {
            newApp.setAppTime(LocalTime.parse(timeString)); // Convert String -> LocalTime
        }

        // Other fields
        newApp.setReason(txtReason.getText());
        newApp.setStatus("Scheduled");

        boolean success = appointmentDAO.addAppointment(newApp);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment scheduled successfully!");
            resetForm();
            loadAppointments(pickDate.getValue(), comboStatusFilter.getValue());
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to schedule appointment. Try again.");
        }
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

import com.clinic.dao.AppointmentDAO;
import com.clinic.models.Appointment;
import com.clinic.models.Patient;
import com.clinic.models.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {

    // ===== TAB PANE =====
    @FXML private TabPane appointmentTabs;
    @FXML private Tab tabListView;
    @FXML private Tab tabFormView;

    // ===== FILTER SECTION =====
    @FXML private DatePicker dateFilter;
    @FXML private ComboBox<String> comboStatusFilter;

    // ===== TABLE SECTION =====
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colPatient;
    @FXML private TableColumn<Appointment, String> colDoctor;
    @FXML private TableColumn<Appointment, String> colReason;
    @FXML private TableColumn<Appointment, String> colStatus;

    // ===== FORM SECTION =====
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private ComboBox<Doctor> comboDoctor;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox<String> comboTimeSlot;
    @FXML private TextArea txtReason;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    // =========================================================
    // INITIALIZE
    // =========================================================
    @FXML
    public void initialize() {

        colTime.setCellValueFactory(data -> data.getValue().timeStringProperty());
        colPatient.setCellValueFactory(data -> data.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(data -> data.getValue().doctorNameProperty());
        colReason.setCellValueFactory(data -> data.getValue().reasonProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        comboStatusFilter.getItems().addAll("All", "Scheduled", "Completed", "Cancelled");
        comboStatusFilter.getSelectionModel().selectFirst();

        // Example time slots
        comboTimeSlot.getItems().addAll("09:00", "10:00", "11:00", "12:00");

        loadAppointments(LocalDate.now(), "All");
    }

    // =========================================================
    // SWITCH TAB (FIXED ISSUE)
    // =========================================================
    @FXML
    private void switchToFormTab() {
        appointmentTabs.getSelectionModel().select(tabFormView);
    }

    // =========================================================
    // LOAD APPOINTMENTS
    // =========================================================
    private void loadAppointments(LocalDate date, String status) {

        List<Appointment> data = appointmentDAO.getAppointmentsByDate(date);

        if (status != null && !status.equals("All")) {
            data = data.stream()
                    .filter(a -> a.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        appointmentList.setAll(data);
        appointmentTable.setItems(appointmentList);
    }

    // =========================================================
    // FILTER METHODS
    // =========================================================
    @FXML
    private void filterByDate() {
        LocalDate selectedDate = dateFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        loadAppointments(selectedDate, comboStatusFilter.getValue());
    }

    @FXML
    private void applyFilters() {
        LocalDate selectedDate = dateFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();

        String status = comboStatusFilter.getValue();
        if (status == null) status = "All";

        loadAppointments(selectedDate, status);
    }

    // =========================================================
    // RESET FORM
    // =========================================================
    @FXML
    private void resetForm() {
        comboPatient.getSelectionModel().clearSelection();
        comboDoctor.getSelectionModel().clearSelection();
        pickDate.setValue(null);
        comboTimeSlot.getSelectionModel().clearSelection();
        txtReason.clear();
    }

    // =========================================================
    // SCHEDULE APPOINTMENT
    // =========================================================
    @FXML
    private void handleSchedule() {

        if (comboPatient.getValue() == null ||
            comboDoctor.getValue() == null ||
            pickDate.getValue() == null ||
            comboTimeSlot.getValue() == null ||
            txtReason.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please fill all fields.");
            return;
        }

        Appointment newApp = new Appointment();

        newApp.setPatientId(comboPatient.getValue().getPatientId());
        newApp.setPatientName(comboPatient.getValue().getName());

        newApp.setDoctorId(comboDoctor.getValue().getDoctorId());
        newApp.setDoctorName(comboDoctor.getValue().getName());

        newApp.setAppDate(pickDate.getValue());
        newApp.setAppTime(LocalTime.parse(comboTimeSlot.getValue()));

        newApp.setReason(txtReason.getText());
        newApp.setStatus("Scheduled");

        boolean success = appointmentDAO.addAppointment(newApp);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Success",
                    "Appointment scheduled successfully.");

            resetForm();
            loadAppointments(pickDate.getValue(), comboStatusFilter.getValue());
            appointmentTabs.getSelectionModel().select(tabListView);
        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "Failed to schedule appointment.");
        }
    }

    // =========================================================
    // ALERT UTILITY
    // =========================================================
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/

package com.clinic.controllers;

import com.clinic.dao.AppointmentDAO;
import com.clinic.dao.PatientDAO;
import com.clinic.dao.DoctorDAO;
import com.clinic.models.Appointment;
import com.clinic.models.Patient;
import com.clinic.models.Doctor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {

    // ===== TAB PANE =====
    @FXML private TabPane appointmentTabs;
    @FXML private Tab tabListView;
    @FXML private Tab tabFormView;

    // ===== FILTER SECTION =====
    @FXML private DatePicker dateFilter;
    @FXML private ComboBox<String> comboStatusFilter;

    // ===== TABLE SECTION =====
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colPatient;
    @FXML private TableColumn<Appointment, String> colDoctor;
    @FXML private TableColumn<Appointment, String> colReason;
    @FXML private TableColumn<Appointment, String> colStatus;

    // ===== FORM SECTION =====
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private ComboBox<Doctor> comboDoctor;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox<String> comboTimeSlot;
    @FXML private TextArea txtReason;

    // ===== DAO =====
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    // =========================================================
    // INITIALIZE
    // =========================================================
    @FXML
    public void initialize() {

        // --- Table Columns ---
        colTime.setCellValueFactory(data -> data.getValue().timeStringProperty());
        colPatient.setCellValueFactory(data -> data.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(data -> data.getValue().doctorNameProperty());
        colReason.setCellValueFactory(data -> data.getValue().reasonProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        // --- Status Filter ---
        comboStatusFilter.getItems().addAll("All", "Scheduled", "Completed", "Cancelled");
        comboStatusFilter.getSelectionModel().selectFirst();

        // --- Time Slots ---
        comboTimeSlot.getItems().addAll(
                "09:00", "10:00", "11:00",
                "12:00", "14:00", "15:00", "16:00"
        );

        // --- Load Patients ---
        List<Patient> patients = patientDAO.getAllPatients();
        comboPatient.setItems(FXCollections.observableArrayList(patients));
        comboPatient.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient p) {
                return (p == null) ? "" : p.getName();
            }
            @Override
            public Patient fromString(String s) { return null; }
        });

        // --- Load Doctors ---
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        comboDoctor.setItems(FXCollections.observableArrayList(doctors));
        comboDoctor.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor d) {
                return (d == null) ? "" : d.getName();
            }
            @Override
            public Doctor fromString(String s) { return null; }
        });

        // --- Set default date ---
        dateFilter.setValue(LocalDate.now());

        // --- Load initial appointments ---
        loadAppointments(
                dateFilter.getValue(),
                comboStatusFilter.getValue()
        );
    }

    // =========================================================
    // SWITCH TAB
    // =========================================================
    @FXML
    private void switchToFormTab() {
        appointmentTabs.getSelectionModel().select(tabFormView);
    }

    // =========================================================
    // LOAD APPOINTMENTS
    // =========================================================
    private void loadAppointments(LocalDate date, String status) {

        List<Appointment> data = appointmentDAO.getAllAppointments();

        if (status != null && !status.equals("All")) {
            data = data.stream()
                    .filter(a -> a.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        appointmentTable.setItems(FXCollections.observableArrayList(data));
    }

    // =========================================================
    // FILTER METHODS
    // =========================================================
    @FXML
    private void filterByDate() {
        LocalDate selectedDate = dateFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        loadAppointments(selectedDate, comboStatusFilter.getValue());
    }

    @FXML
    private void applyFilters() {
        LocalDate selectedDate = dateFilter.getValue();
        if (selectedDate == null) selectedDate = LocalDate.now();
        String status = comboStatusFilter.getValue();
        if (status == null) status = "All";
        loadAppointments(selectedDate, status);
    }

    // =========================================================
    // RESET FORM
    // =========================================================
    @FXML
    private void resetForm() {
        comboPatient.getSelectionModel().clearSelection();
        comboDoctor.getSelectionModel().clearSelection();
        pickDate.setValue(null);
        comboTimeSlot.getSelectionModel().clearSelection();
        txtReason.clear();
    }

    // =========================================================
    // SCHEDULE APPOINTMENT
    // =========================================================
    @FXML
    private void handleSchedule() {

        if (comboPatient.getValue() == null ||
            comboDoctor.getValue() == null ||
            pickDate.getValue() == null ||
            comboTimeSlot.getValue() == null ||
            txtReason.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return;
        }

        Appointment newApp = new Appointment();
        newApp.setPatientId(comboPatient.getValue().getPatientId());
        newApp.setPatientName(comboPatient.getValue().getName());
        newApp.setDoctorId(comboDoctor.getValue().getDoctorId());
        newApp.setDoctorName(comboDoctor.getValue().getName());
        newApp.setAppDate(pickDate.getValue());
        newApp.setAppTime(LocalTime.parse(comboTimeSlot.getValue()));
        newApp.setReason(txtReason.getText());
        newApp.setStatus("Scheduled");

        boolean success = appointmentDAO.addAppointment(newApp);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment scheduled successfully.");
            resetForm();
            loadAppointments(pickDate.getValue(), comboStatusFilter.getValue());
            appointmentTabs.getSelectionModel().select(tabListView);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to schedule appointment.");
        }
    }

    // =========================================================
    // ALERT UTILITY
    // =========================================================
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
