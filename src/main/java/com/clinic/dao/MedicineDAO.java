package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Medicine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    public boolean addMedicine(Medicine med) {
        String sql = "INSERT INTO medicines (name, type, stock_quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, med.getName());
            pstmt.setString(2, med.getType());
            pstmt.setInt(3, med.getStockQuantity());
            pstmt.setDouble(4, med.getUnitPrice());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Medicine(
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("stock_quantity"),
                    rs.getDouble("unit_price")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public boolean updateStock(int medicineId, int quantityChange) {

        String sql = "UPDATE medicines SET stock_quantity = stock_quantity + ? " +
                     "WHERE medicine_id = ? AND (stock_quantity + ? >= 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantityChange);
            pstmt.setInt(2, medicineId);
            pstmt.setInt(3, quantityChange);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // Check Low Stock 
    public List<Medicine> getLowStockAlert(int threshold) {
        List<Medicine> lowStockList = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE stock_quantity <= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lowStockList.add(new Medicine(
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("stock_quantity"),
                    rs.getDouble("unit_price")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lowStockList;
    }
}