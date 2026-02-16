package com.clinic.models;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private IntegerProperty appointmentId = new SimpleIntegerProperty();
    private IntegerProperty patientId = new SimpleIntegerProperty();
    private IntegerProperty doctorId = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> appDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalTime> appTime = new SimpleObjectProperty<>();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty reason = new SimpleStringProperty();
    private StringProperty doctorName = new SimpleStringProperty();
    private StringProperty patientName = new SimpleStringProperty();

    public Appointment() {}

    // ==== Appointment ID ====
    public int getAppointmentId() { return appointmentId.get(); }
    public void setAppointmentId(int id) { appointmentId.set(id); }
    public IntegerProperty appointmentIdProperty() { return appointmentId; }

    // ==== Patient ID ====
    public int getPatientId() { return patientId.get(); }
    public void setPatientId(int id) { patientId.set(id); }
    public IntegerProperty patientIdProperty() { return patientId; }

    // ==== Doctor ID ====
    public int getDoctorId() { return doctorId.get(); }
    public void setDoctorId(int id) { doctorId.set(id); }
    public IntegerProperty doctorIdProperty() { return doctorId; }

    // ==== Appointment Date ====
    public LocalDate getAppDate() { return appDate.get(); }
    public void setAppDate(LocalDate date) { appDate.set(date); }
    public ObjectProperty<LocalDate> appDateProperty() { return appDate; }

    // ==== Appointment Time ====
    public LocalTime getAppTime() { return appTime.get(); }
    public void setAppTime(LocalTime t) { appTime.set(t); }
    public StringProperty timeStringProperty() { 
        return new SimpleStringProperty(appTime.get() != null ? appTime.get().toString() : ""); 
    }

    // ==== Status ====
    public String getStatus() { return status.get(); }
    public void setStatus(String s) { status.set(s); }
    public StringProperty statusProperty() { return status; }

    // ==== Reason ====
    public String getReason() { return reason.get(); }
    public void setReason(String r) { reason.set(r); }
    public StringProperty reasonProperty() { return reason; }

    // ==== Doctor Name ====
    public String getDoctorName() { return doctorName.get(); }
    public void setDoctorName(String n) { doctorName.set(n); }
    public StringProperty doctorNameProperty() { return doctorName; }

    // ==== Patient Name ====
    public String getPatientName() { return patientName.get(); }
    public void setPatientName(String n) { patientName.set(n); }
    public StringProperty patientNameProperty() { return patientName; }
}
