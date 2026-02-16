package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RoleDAO {

    public Map<Integer, String> getRoleMapping() {
        Map<Integer, String> roleMap = new HashMap<>();
        String sql = "SELECT * FROM roles";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                roleMap.put(rs.getInt("role_id"), rs.getString("role_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleMap;
    }

    public String getRoleNameById(int roleId) {
        String sql = "SELECT role_name FROM roles WHERE role_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, roleId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("role_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
}