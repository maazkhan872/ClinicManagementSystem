package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Billing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    // Generate Invoice (Real-time Bill Creation)
    public boolean generateInvoice(Billing b) {
        String sql = "INSERT INTO billing (patient_id, appointment_id, total_amount, payment_status, billing_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, b.getPatientId());
            pstmt.setInt(2, b.getAppointmentId());
            pstmt.setDouble(3, b.getTotalAmount());
            pstmt.setString(4, b.getPaymentStatus()); // e.g., 'Paid', 'Pending'
            pstmt.setDate(5, Date.valueOf(b.getBillingDate()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                updateAppointmentStatusAfterBilling(b.getAppointmentId());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get All Bills (For Finance Reports)
    public List<Billing> getAllBills() {
        List<Billing> billList = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY billing_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                billList.add(new Billing(
                    rs.getInt("bill_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("appointment_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("payment_status"),
                    rs.getDate("billing_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    // Payment Status Change (e.g., Pending to Paid)
    public boolean updatePaymentStatus(int billId, String newStatus) {
        String sql = "UPDATE billing SET payment_status = ? WHERE bill_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, billId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to update appointment when bill is generated
    private void updateAppointmentStatusAfterBilling(int appointmentId) {
        String sql = "UPDATE appointments SET status = 'Completed' WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
