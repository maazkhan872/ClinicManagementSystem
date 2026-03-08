package com.clinic.models;

import java.util.Objects;
import com.clinic.models.PrescriptionItem;

public class PrescriptionItem {
    
    private int id;
    private int prescriptionId;
    private int medicineId;
    private int quantity;
    private String dosage;

    // Default Constructor 
    public PrescriptionItem() {}

    public PrescriptionItem(int prescriptionId, int medicineId, int quantity, String dosage) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.dosage = dosage;
    }

    // Getters and Setters
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    // Overriding toString() for easier logging and debugging
    @Override
    public String toString() {
        return "PrescriptionItem{" +
                "id=" + id +
                ", prescriptionId=" + prescriptionId +
                ", medicineId=" + medicineId +
                ", quantity=" + quantity +
                ", dosage='" + dosage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionItem that = (PrescriptionItem) o;
        return id == that.id && 
               prescriptionId == that.prescriptionId && 
               medicineId == that.medicineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prescriptionId, medicineId);
    }
}