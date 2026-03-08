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
