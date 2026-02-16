package com.clinic.models;
import java.time.LocalDate;

public class Billing {
    private int billId;
    private int patientId;
    private int appointmentId;
    private double totalAmount;
    private String paymentStatus;
    private LocalDate billingDate;
    
    public Billing() {
    }
    
    public Billing(int billId, int patientId, int appointmentId, double totalAmount, String paymentStatus, LocalDate billingDate) {
        this.billId = billId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.billingDate = billingDate;
    }

    public Billing(int patientId, int appointmentId, double totalAmount, String paymentStatus, LocalDate billingDate) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.billingDate = billingDate;
    }

    //  Getters and Setters 

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDate getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDate billingDate) { this.billingDate = billingDate; }
}
