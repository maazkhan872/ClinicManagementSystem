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

    //  TAB PANE 
    @FXML private TabPane appointmentTabs;
    @FXML private Tab tabListView;
    @FXML private Tab tabFormView;

    // FILTER SECTION 
    @FXML private DatePicker dateFilter;
    @FXML private ComboBox<String> comboStatusFilter;

    // TABLE SECTION 
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> colTime;
    @FXML private TableColumn<Appointment, String> colPatient;
    @FXML private TableColumn<Appointment, String> colDoctor;
    @FXML private TableColumn<Appointment, String> colReason;
    @FXML private TableColumn<Appointment, String> colStatus;

    // FORM SECTION
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private ComboBox<Doctor> comboDoctor;
    @FXML private DatePicker pickDate;
    @FXML private ComboBox<String> comboTimeSlot;
    @FXML private TextArea txtReason;

    // DAO 
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    // INITIALIZE
    @FXML
    public void initialize() {

        // Table Columns 
        colTime.setCellValueFactory(data -> data.getValue().timeStringProperty());
        colPatient.setCellValueFactory(data -> data.getValue().patientNameProperty());
        colDoctor.setCellValueFactory(data -> data.getValue().doctorNameProperty());
        colReason.setCellValueFactory(data -> data.getValue().reasonProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());

        // Status Filter
        comboStatusFilter.getItems().addAll("All", "Scheduled", "Completed", "Cancelled");
        comboStatusFilter.getSelectionModel().selectFirst();

        // Time Slots
        comboTimeSlot.getItems().addAll(
                "09:00", "10:00", "11:00",
                "12:00", "14:00", "15:00", "16:00"
        );

        // Load Patients
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

        // Load Doctors
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

        // Set default date
        dateFilter.setValue(LocalDate.now());

        // Load initial appointments 
        loadAppointments(
                dateFilter.getValue(),
                comboStatusFilter.getValue()
        );
    }

    // SWITCH TAB
    @FXML
    private void switchToFormTab() {
        appointmentTabs.getSelectionModel().select(tabFormView);
    }

    // LOAD APPOINTMENTS
    private void loadAppointments(LocalDate date, String status) {

        List<Appointment> data = appointmentDAO.getAllAppointments();

        if (status != null && !status.equals("All")) {
            data = data.stream()
                    .filter(a -> a.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        appointmentTable.setItems(FXCollections.observableArrayList(data));
    }

    // FILTER METHODS
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

    // RESET FORM
    @FXML
    private void resetForm() {
        comboPatient.getSelectionModel().clearSelection();
        comboDoctor.getSelectionModel().clearSelection();
        pickDate.setValue(null);
        comboTimeSlot.getSelectionModel().clearSelection();
        txtReason.clear();
    }

    // SCHEDULE APPOINTMENT
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

    // ALERT UTILITY
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
