/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Appointment;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public boolean isDoctorBusy(int doctorId, LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND app_date = ? AND app_time = ? AND status != 'Cancelled'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, doctorId);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // CREATE: Book an Appointment
    public boolean bookAppointment(Appointment app) {

        if (isDoctorBusy(app.getDoctorId(), app.getAppDate(), app.getAppTime())) {
            System.out.println("Conflict: Doctor already has an appointment at this time!");
            return false;
        }

        String sql = "INSERT INTO appointments (patient_id, doctor_id, app_date, app_time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, app.getPatientId());
            pstmt.setInt(2, app.getDoctorId());
            pstmt.setDate(3, Date.valueOf(app.getAppDate()));
            pstmt.setTime(4, Time.valueOf(app.getAppTime()));
            pstmt.setString(5, app.getStatus()); // e.g., 'Scheduled'
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get appointments for a specific date (For Dashboard)
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE app_date = ? ORDER BY app_time ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Appointment app = new Appointment(
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getDate("app_date").toLocalDate(),
                    rs.getTime("app_time").toLocalTime(),
                    rs.getString("status")
                );
                app.setAppointmentId(rs.getInt("appointment_id"));
                list.add(app);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        
        // JOIN Query: Appointments ke saath Doctor (from users) aur Patient ka naam uthana
        String sql = "SELECT a.*, u.username AS doctor_name, p.name AS patient_name " +
                     "FROM appointments a " +
                     "JOIN users u ON a.doctor_id = u.user_id " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "WHERE a.app_date = ? ORDER BY a.app_time ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("appointment_id"));
                app.setPatientId(rs.getInt("patient_id"));
                app.setDoctorId(rs.getInt("doctor_id"));
                app.setAppDate(rs.getDate("app_date").toLocalDate());
                app.setAppTime(rs.getTime("app_time").toLocalTime());
                app.setStatus(rs.getString("status"));
                
                // Ye do naye fields hain jo JOIN se mil rahe hain
                app.setDoctorName(rs.getString("doctor_name"));
                app.setPatientName(rs.getString("patient_name"));
                
                list.add(app);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }

    // Change Status (e.g., Scheduled -> Completed or Cancelled)
    public boolean updateStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, appointmentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
*/

/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Appointment;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        // JOIN Query to get Names instead of just IDs
        String sql = "SELECT a.*, p.name as patient_name, u.username as doctor_name " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "JOIN users u ON a.doctor_id = u.user_id " +
                     "WHERE a.app_date = ? ORDER BY a.app_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("appointment_id"));
                app.setAppDate(rs.getDate("app_date").toLocalDate());
                app.setAppTime(rs.getTime("app_time").toLocalTime());
                app.setStatus(rs.getString("status"));
                
                // Mapping Names from JOIN
                app.setPatientName(rs.getString("patient_name"));
                app.setDoctorName(rs.getString("doctor_name"));
                
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}*/

/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p.name as patient_name, u.username as doctor_name " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "JOIN users u ON a.doctor_id = u.user_id " +
                     "WHERE a.app_date = ? ORDER BY a.app_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Add Appointment method
    public boolean addAppointment(Appointment app) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, app_date, app_time, status, reason) " +
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

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/

/*package com.clinic.dao;

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

        } catch (Exception e) {

            e.printStackTrace();

        }

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

        }
        catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }

}*/

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

