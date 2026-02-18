/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient p) {

        String sql = "INSERT INTO patients (name, phone, gender, address, age, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getPhone());
            pstmt.setString(3, p.getGender());
            pstmt.setString(4, p.getAddress());
            pstmt.setInt(5, p.getAge()); 
            pstmt.setDate(6, Date.valueOf(p.getDob())); 
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Constructor mapping with all 7 fields
                Patient p = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getInt("age"), 
                    rs.getDate("date_of_birth").toLocalDate()
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}*/

/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, phone, gender, address, age, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getPhone());
            pstmt.setString(3, p.getGender());
            pstmt.setString(4, p.getAddress());
            pstmt.setInt(5, p.getAge());

            // ✅ Handle DOB safely
            if (p.getDob() != null) {
                pstmt.setDate(6, Date.valueOf(p.getDob()));
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getInt("age"),
                    rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}*/

package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public boolean addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, phone, gender, address, age, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getPhone());
            pstmt.setString(3, p.getGender());
            pstmt.setString(4, p.getAddress());
            pstmt.setInt(5, p.getAge());

            if (p.getDob() != null) {
                pstmt.setDate(6, Date.valueOf(p.getDob()));
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id DESC\r\n"
        		+ "";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getInt("age"),
                        rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

