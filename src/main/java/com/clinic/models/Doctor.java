package com.clinic.models;

public class Doctor {
    private int doctorId;
    private String name;

    public Doctor(int doctorId, String name) {
        this.doctorId = doctorId;
        this.name = name;
    }

    public int getDoctorId() { return doctorId; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; } 
}

