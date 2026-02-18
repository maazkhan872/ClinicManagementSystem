package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.clinic.models.PrescriptionItem;
public class PrescriptionItemDAO {
    
    public boolean addItemsToPrescription(int prescriptionId, int medicineId, int quantity, String dosage) {
        String sql = "INSERT INTO prescription_items (prescription_id, medicine_id, quantity, dosage) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, prescriptionId);
            pstmt.setInt(2, medicineId);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, dosage);
            
            int result = pstmt.executeUpdate();
            
            // Medicine stock update
            if (result > 0) {
                new MedicineDAO().updateStock(medicineId, -quantity);
            }
            return result > 0;
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
    
    public boolean addItem(PrescriptionItem item) {
        return addItemsToPrescription(
            item.getPrescriptionId(), 
            item.getMedicineId(), 
            item.getQuantity(), 
            item.getDosage()
        );
    }

  
        public List<PrescriptionItem> getItemsByPrescriptionId(int pId) {
            List<PrescriptionItem> items = new ArrayList<>();
            // Join taake medicine ka naam bhi mil jaye
            String sql = "SELECT pi.*, m.name FROM prescription_items pi " +
                         "JOIN medicines m ON pi.medicine_id = m.medicine_id WHERE pi.prescription_id = ?";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, pId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PrescriptionItem item = new PrescriptionItem();
                    item.setMedicineId(rs.getInt("medicine_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setDosage(rs.getString("dosage"));
                    // Agar aapke model mein medicineName field hai to set karein
                    items.add(item);
                }
            } catch (SQLException e) { e.printStackTrace(); }
            return items;
        }
        
    }
