package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Prescription;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    public int addPrescription(Prescription p) {
        String sql = "INSERT INTO prescriptions (patient_id, doctor_id, symptoms, diagnosis, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, p.getPatientId());
            pstmt.setInt(2, p.getDoctorId());
            pstmt.setString(3, p.getSymptoms());
            pstmt.setString(4, p.getDiagnosis());
            pstmt.setDate(5, Date.valueOf(p.getDate()));
            
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1); 
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return -1;
    }

    public List<Prescription> getPrescriptionsByPatient(int patientId) {
        List<Prescription> history = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions WHERE patient_id = ? ORDER BY date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                history.add(new Prescription(
                    rs.getInt("prescription_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("symptoms"),
                    rs.getString("diagnosis"),
                    rs.getDate("date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}