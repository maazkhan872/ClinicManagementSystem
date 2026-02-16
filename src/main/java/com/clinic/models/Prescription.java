package com.clinic.models;
import java.time.LocalDate;

public class Prescription {
    private int prescriptionId;
    private int patientId;
    private int doctorId;
    private String symptoms;
    private String diagnosis;
    private LocalDate date;
    
    public Prescription() {}

    public Prescription(int prescriptionId, int patientId, int doctorId, String symptoms, String diagnosis, LocalDate date) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.date = date;
    }

    // Getters and Setters 
    
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}