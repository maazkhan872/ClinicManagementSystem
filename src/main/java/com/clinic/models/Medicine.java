package com.clinic.models;

public class Medicine {
    private int medicineId;
    private String name;
    private String type;
    private int stockQuantity;
    private double unitPrice;

    public Medicine(int medicineId, String name, String type, int stockQuantity, double unitPrice) {
        this.medicineId = medicineId;
        this.name = name;
        this.type = type;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }
    
    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    // Getters and Setters
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}