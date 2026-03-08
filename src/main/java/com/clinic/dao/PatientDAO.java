package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient p) {

        String sql =
        "INSERT INTO patients (name, phone, gender, address, age, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getPhone());
            pstmt.setString(3, p.getGender());
            pstmt.setString(4, p.getAddress());
            pstmt.setInt(5, p.getAge());

            if (p.getDob() != null)
                pstmt.setDate(6, Date.valueOf(p.getDob()));
            else
                pstmt.setNull(6, Types.DATE);

            return pstmt.executeUpdate() > 0;

        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    public List<Patient> getAllPatients() {

        List<Patient> list = new ArrayList<>();

        String sql =
        "SELECT patient_id, name, phone, gender, address, age, date_of_birth FROM patients ORDER BY patient_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Patient p = new Patient();

                p.setPatientId(rs.getInt("patient_id"));

                p.setName(rs.getString("name"));

                p.setPhone(rs.getString("phone"));

                p.setGender(rs.getString("gender"));

                p.setAddress(rs.getString("address"));

                p.setAge(rs.getInt("age"));

                Date dob = rs.getDate("date_of_birth");

                if(dob != null)
                    p.setDob(dob.toLocalDate());

                list.add(p);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return list;

    }
}

