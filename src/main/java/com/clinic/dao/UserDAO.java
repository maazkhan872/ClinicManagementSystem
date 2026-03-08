package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.User;
import com.clinic.security.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // LOGIN 
    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    String dbPassword = rs.getString("password");

                    if (dbPassword.equals(password) || dbPassword.equals(PasswordUtil.hashPassword(password))) {
                        return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getInt("role_id"),
                            rs.getString("email")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User validateLogin(String username, String password) {
        return login(username, password);
    }

    // REGISTER USER 
    public boolean registerUser(User user, String plainPassword) {

        String sql = "INSERT INTO users (username, password, role_id, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Hash password for new users
            String hashedPassword = PasswordUtil.hashPassword(plainPassword);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, hashedPassword);
            pstmt.setInt(3, user.getRoleId());
            pstmt.setString(4, user.getEmail());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FETCH ALL USERS
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getInt("role_id"),
                        rs.getString("email")
                );

                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // HELPER MAIN 
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        //  Add new admin user
        User admin = new User(0, "admin", 1, "admin@example.com"); 
        boolean added = dao.registerUser(admin, "admin123"); // password hashed automatically

        System.out.println("Admin added? " + added);

        // Add new doctor
        User doctor = new User(0, "doctor", 2, "doctor@example.com"); 
        boolean addedDoc = dao.registerUser(doctor, "doc123"); // password hashed automatically

        System.out.println("Doctor added? " + addedDoc);
    }
}