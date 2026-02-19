package com.clinic.models;

import javafx.beans.property.*;

public class BillingItem {

    private final StringProperty itemName;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty total;

    public BillingItem(String itemName, int quantity, double price) {
        this.itemName = new SimpleStringProperty(itemName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.total = new SimpleDoubleProperty(quantity * price);
    }

    public StringProperty itemNameProperty() { return itemName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty priceProperty() { return price; }
    public DoubleProperty totalProperty() { return total; }
    
    public String getItemName() { return itemName.get(); }   // ✅ ADD THIS

    public double getTotal() { return total.get(); }
}
