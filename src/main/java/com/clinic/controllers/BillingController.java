/*package com.clinic.controllers;

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
}*/

/*package com.clinic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import com.clinic.dao.PatientDAO;
import com.clinic.dao.BillingDAO;
import com.clinic.models.Patient;
import com.clinic.models.Billing;
import com.clinic.models.BillingItem;

import java.time.LocalDate;
import java.util.List;

public class BillingController {

    @FXML private ComboBox<Patient> comboPatient;

    @FXML private TableView<BillingItem> billingTable;
    @FXML private TableColumn<BillingItem, String> colItem;
    @FXML private TableColumn<BillingItem, Integer> colQty;
    @FXML private TableColumn<BillingItem, Double> colPrice;
    @FXML private TableColumn<BillingItem, Double> colTotal;

    @FXML private Text txtSubtotal;
    @FXML private Text txtTax;
    @FXML private Text txtGrandTotal;

    @FXML private ComboBox<String> comboPaymentMethod;

    private ObservableList<BillingItem> billItems =
            FXCollections.observableArrayList();

    private PatientDAO patientDAO = new PatientDAO();
    private BillingDAO billingDAO = new BillingDAO();

    @FXML
    public void initialize() {

        comboPaymentMethod.getItems().addAll("Paid", "Pending");

        loadPatientsFromDatabase();

        colItem.setCellValueFactory(data ->
                data.getValue().itemNameProperty());

        colQty.setCellValueFactory(data ->
                data.getValue().quantityProperty().asObject());

        colPrice.setCellValueFactory(data ->
                data.getValue().priceProperty().asObject());

        colTotal.setCellValueFactory(data ->
                data.getValue().totalProperty().asObject());

        billingTable.setItems(billItems);
    }


    // ✅ LOAD REAL PATIENTS FROM DATABASE

    private void loadPatientsFromDatabase() {

        List<Patient> patientList =
                patientDAO.getAllPatients();

        comboPatient.setItems(
                FXCollections.observableArrayList(patientList)
        );
    }



    // LOAD BILL ITEMS

    @FXML
    private void loadPatientData() {

        Patient selected =
                comboPatient.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showAlert("Select Patient First");
            return;
        }

        billItems.clear();

        billItems.add(
                new BillingItem("Consultation",1,1500)
        );

        billItems.add(
                new BillingItem("Medicine",2,500)
        );

        calculateTotals();
    }



    // GENERATE INVOICE DATABASE SAVE

    @FXML
    private void handleGenerateInvoice() {

        Patient selected =
                comboPatient.getSelectionModel().getSelectedItem();

        if(selected==null){

            showAlert("Select Patient");

            return;
        }


        String paymentStatus =
                comboPaymentMethod.getValue();


        if(paymentStatus==null){

            showAlert("Select Payment Method");

            return;
        }


        double total =
                Double.parseDouble(txtGrandTotal.getText());



        Billing bill =
                new Billing(

                        selected.getPatientId(),

                        1,

                        total,

                        paymentStatus,

                        LocalDate.now()

                );



        boolean saved =
                billingDAO.generateInvoice(bill);



        if(saved){

            showAlert("Invoice Saved Successfully");

        }

        else{

            showAlert("Error Saving Invoice");

        }

    }




    private void calculateTotals() {

        double subtotal =
                billItems.stream()
                        .mapToDouble(BillingItem::getTotal)
                        .sum();


        double tax =
                subtotal * 0.05;


        double grand =
                subtotal + tax;


        txtSubtotal.setText(String.valueOf(subtotal));

        txtTax.setText(String.valueOf(tax));

        txtGrandTotal.setText(String.valueOf(grand));
    }



    private void showAlert(String msg){

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setContentText(msg);

        alert.showAndWait();

    }

}*/

package com.clinic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import com.clinic.dao.PatientDAO;
import com.clinic.dao.BillingDAO;
import com.clinic.models.Patient;
import com.clinic.models.BillingItem;
import com.clinic.models.Billing;

import java.time.LocalDate;
import java.util.List;


public class BillingController {

    @FXML private ComboBox<Patient> comboPatient;
    @FXML private TableView<BillingItem> billingTable;
    @FXML private TableColumn<BillingItem, String> colItem;
    @FXML private TableColumn<BillingItem, Integer> colQty;
    @FXML private TableColumn<BillingItem, Double> colPrice;
    @FXML private TableColumn<BillingItem, Double> colTotal;

    @FXML private Text txtSubtotal;
    @FXML private Text txtTax;
    @FXML private Text txtGrandTotal;

    @FXML private ComboBox<String> comboPaymentMethod;

    private ObservableList<BillingItem> billItems = FXCollections.observableArrayList();
    private PatientDAO patientDAO = new PatientDAO();
    private BillingDAO billingDAO = new BillingDAO();

    @FXML
    public void initialize() {
        //comboPaymentMethod.getItems().addAll("Paid", "Pending");

    	comboPaymentMethod.getItems().addAll(

    	        "Cash",
    	        "Debit Card",
    	        "Credit Card",
    	        "JazzCash",
    	        "EasyPaisa"

    	);
    	
        loadPatientsFromDatabase();

        colItem.setCellValueFactory(data -> data.getValue().itemNameProperty());
        colQty.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        colPrice.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        colTotal.setCellValueFactory(data -> data.getValue().totalProperty().asObject());

        billingTable.setItems(billItems);
    }

    private void loadPatientsFromDatabase() {
        List<Patient> patientList = patientDAO.getAllPatients();
        comboPatient.setItems(FXCollections.observableArrayList(patientList));
    }

 
    @FXML
    private void loadPatientData(ActionEvent event) {

        Patient selected = comboPatient.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showAlert("Select Patient First");

            return;

        }

        billItems.clear();

        List<BillingItem> items =
                billingDAO.getBillingItemsByPatient(
                        selected.getPatientId()
                );

        if (items.isEmpty()) {

            showAlert("No Pending Bills Found");

            return;

        }

        billItems.addAll(items);

        calculateTotals();

    }


    @FXML
    private void handleGenerateInvoice(ActionEvent event) {
        Patient selected = comboPatient.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select Patient");
            return;
        }

        String paymentStatus = comboPaymentMethod.getValue();
        if (paymentStatus == null) {
            showAlert("Select Payment Method");
            return;
        }

        double total = Double.parseDouble(txtGrandTotal.getText());

        int appointmentId =
        		billingDAO.getLatestAppointmentId(
        		        selected.getPatientId()
        		);

        		Billing bill =
        		        new Billing(
        		                selected.getPatientId(),
        		                appointmentId,
        		                total,
        		                paymentStatus,
        		                LocalDate.now()
        		        );

        boolean saved = billingDAO.generateInvoice(bill);
        if (saved) {
            showAlert("Invoice Saved Successfully");
        } else {
            showAlert("Error Saving Invoice");
        }
    }

    @FXML
    private void handlePrint(ActionEvent event) {
        // Print stub
        System.out.println("Print button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Printing Invoice...");
        alert.showAndWait();
    }

    private void calculateTotals() {
        double subtotal = billItems.stream().mapToDouble(BillingItem::getTotal).sum();
        double tax = subtotal * 0.05;
        double grand = subtotal + tax;

        txtSubtotal.setText(String.format("%.2f", subtotal));
        txtTax.setText(String.format("%.2f", tax));
        txtGrandTotal.setText(String.format("%.2f", grand));
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}




