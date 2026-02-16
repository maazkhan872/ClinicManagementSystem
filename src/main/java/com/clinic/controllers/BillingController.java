package com.clinic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.clinic.models.BillingItem;

public class BillingController {

    // LEFT SIDE
    @FXML private ComboBox<String> comboPatient;
    @FXML private TableView<BillingItem> billingTable;
    @FXML private TableColumn<BillingItem, String> colItem;
    @FXML private TableColumn<BillingItem, Integer> colQty;
    @FXML private TableColumn<BillingItem, Double> colPrice;
    @FXML private TableColumn<BillingItem, Double> colTotal;

    // RIGHT SIDE SUMMARY
    @FXML private Text txtSubtotal;
    @FXML private Text txtTax;
    @FXML private Text txtGrandTotal;
    @FXML private ComboBox<String> comboPaymentMethod;

    private ObservableList<BillingItem> billItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Payment methods
        comboPaymentMethod.getItems().addAll("Cash", "Card", "Online");
        
        // Sample Patients (later DB se load kar sakte ho)
        comboPatient.getItems().addAll("Patient #101", "Patient #102");

        // Table column mapping
        colItem.setCellValueFactory(data -> data.getValue().itemNameProperty());
        colQty.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        colPrice.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        colTotal.setCellValueFactory(data -> data.getValue().totalProperty().asObject());

        billingTable.setItems(billItems);
    }

    // 🔹 Load Patient Data Button
    @FXML
    private void loadPatientData() {

        if (comboPatient.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Select Patient", "Please select a patient first.");
            return;
        }

        // Demo Data (replace with DB call later)
        billItems.clear();
        billItems.add(new BillingItem("Consultation", 1, 1500));
        billItems.add(new BillingItem("Medicine", 2, 500));

        calculateTotals();
    }

    // 🔹 Generate Invoice
    @FXML
    private void handleGenerateInvoice() {

        if (comboPaymentMethod.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Payment Method Missing", "Please select payment method.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Success",
                "Invoice generated successfully!\nTotal: " + txtGrandTotal.getText());
    }

    // 🔹 Print Receipt
    @FXML
    private void handlePrint() {
        showAlert(Alert.AlertType.INFORMATION, "Print",
                "Printing receipt...");
    }

    // 🔹 Calculate Totals
    private void calculateTotals() {

        double subtotal = billItems.stream()
                .mapToDouble(BillingItem::getTotal)
                .sum();

        double tax = subtotal * 0.05;
        double grandTotal = subtotal + tax;

        txtSubtotal.setText(String.format("%.2f", subtotal));
        txtTax.setText(String.format("%.2f", tax));
        txtGrandTotal.setText(String.format("%.2f", grandTotal));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
