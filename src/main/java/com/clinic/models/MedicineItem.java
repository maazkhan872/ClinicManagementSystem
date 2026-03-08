package com.clinic.models;

public class MedicineItem {

    private String medicine;
    private int quantity;
    private String dosage;

    public MedicineItem(String medicine, int quantity, String dosage) {
        this.medicine = medicine;
        this.quantity = quantity;
        this.dosage = dosage;
    }

    public String getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }
    public String getDosage() { return dosage; }
}
