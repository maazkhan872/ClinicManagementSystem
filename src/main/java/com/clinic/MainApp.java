package com.clinic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML Path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/clinic/views/Login.fxml"));
            Parent root = loader.load();
            
            // Scene Setup
            Scene scene = new Scene(root);
            
            // CSS Setup 
            String css = getClass().getResource("/com/clinic/styles/Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            primaryStage.setTitle("Clinic Management System - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); 
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("MainApp Error: Could not start the application.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}