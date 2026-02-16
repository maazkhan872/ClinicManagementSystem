package com.clinic.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Database Credentials
    private static final String URL = "jdbc:mysql://localhost:3306/clinic_management_db"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            
            if (connection == null || connection.isClosed()) {
               
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to Clinic Database!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found! Check your pom.xml dependencies.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check URL, Username, or Password.");
            e.printStackTrace();
        }
        return connection;
    }
}