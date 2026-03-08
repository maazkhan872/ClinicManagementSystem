package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // GET ALL APPOINTMENTS
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql =
            "SELECT a.*, p.name AS patient_name, u.username AS doctor_name " +
            "FROM appointments a " +
            "JOIN patients p ON a.patient_id = p.patient_id " +
            "JOIN users u ON a.doctor_id = u.user_id " +
            "ORDER BY a.app_date DESC, a.app_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("appointment_id"));
                app.setPatientId(rs.getInt("patient_id"));
                app.setDoctorId(rs.getInt("doctor_id"));
                app.setAppDate(rs.getDate("app_date").toLocalDate());
                app.setAppTime(rs.getTime("app_time").toLocalTime());
                app.setStatus(rs.getString("status"));
                app.setReason(rs.getString("reason"));
                app.setPatientName(rs.getString("patient_name"));
                app.setDoctorName(rs.getString("doctor_name"));
                list.add(app);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ADD APPOINTMENT
    public boolean addAppointment(Appointment app) {
        String sql =
            "INSERT INTO appointments " +
            "(patient_id, doctor_id, app_date, app_time, status, reason) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, app.getPatientId());
            pstmt.setInt(2, app.getDoctorId());
            pstmt.setDate(3, Date.valueOf(app.getAppDate()));
            pstmt.setTime(4, Time.valueOf(app.getAppTime()));
            pstmt.setString(5, app.getStatus());
            pstmt.setString(6, app.getReason());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // GET PATIENT NAMES WITH APPOINTMENTS
    public List<String> getPatientsWithAppointments() {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT DISTINCT p.name " +
            "FROM patients p " +
            "JOIN appointments a ON p.patient_id = a.patient_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) list.add(rs.getString("name"));

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }
}

