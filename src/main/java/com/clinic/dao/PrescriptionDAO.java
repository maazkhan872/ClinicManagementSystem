/*package com.clinic.dao;

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
}*/

/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.MedicineItem;

import java.sql.*;
import java.util.List;

public class PrescriptionDAO {

    // Save prescription and return generated prescription_id
    public int savePrescription(int patientId, int doctorId, String symptoms, String diagnosis) throws SQLException {
        String sql = "INSERT INTO prescriptions (patient_id, doctor_id, symptoms, diagnosis) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, symptoms);
            pstmt.setString(4, diagnosis);

            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        }
        return -1;
    }

    // Save prescription items
    public boolean savePrescriptionItems(int prescriptionId, List<MedicineItem> items) throws SQLException {
        String sql = "INSERT INTO prescription_items (prescription_id, medicine_id, quantity, dosage) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (MedicineItem item : items) {
                int medicineId = getMedicineIdByName(item.getMedicine());
                pstmt.setInt(1, prescriptionId);
                pstmt.setInt(2, medicineId);
                pstmt.setInt(3, item.getQuantity());
                pstmt.setString(4, item.getDosage());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
        return true;
    }

    // Helper: get medicine_id by name
    private int getMedicineIdByName(String name) throws SQLException {
        String sql = "SELECT medicine_id FROM medicines WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("medicine_id");
        }
        return -1;
    }
}
*/

package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.MedicineItem;

import java.sql.*;
import java.util.List;

public class PrescriptionDAO {

    public int savePrescription(
            int patientId,
            int doctorId,
            String symptoms,
            String diagnosis
    ) throws SQLException {

        String sql =
                "INSERT INTO prescriptions " +
                "(patient_id, doctor_id, symptoms, diagnosis) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );

        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        ps.setString(3, symptoms);
        ps.setString(4, diagnosis);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next())
            return rs.getInt(1);

        return -1;
    }


    public void savePrescriptionItems(
            int prescriptionId,
            List<MedicineItem> items
    ) throws SQLException {

        String sql =
                "INSERT INTO prescription_items " +
                "(prescription_id, medicine_id, quantity, dosage) " +
                "VALUES (?, ?, ?, ?)";

        Connection conn =
                DatabaseConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(sql);

        for (MedicineItem item : items) {

            int medicineId =
                    getMedicineIdByName(item.getMedicine());

            ps.setInt(1, prescriptionId);
            ps.setInt(2, medicineId);
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getDosage());

            ps.executeUpdate();
        }
    }


    private int getMedicineIdByName(
            String name
    ) throws SQLException {

        String sql =
                "SELECT medicine_id " +
                "FROM medicines WHERE name=?";

        Connection conn =
                DatabaseConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        if (rs.next())
            return rs.getInt("medicine_id");

        throw new SQLException(
                "Medicine not found: " + name
        );
    }
}
