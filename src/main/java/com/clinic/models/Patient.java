package com.clinic.models;

import java.time.LocalDate;

public class Patient {

    private int patientId;
    private String name;
    private String phone;
    private String gender;
    private String address;
    private int age;
    private LocalDate dob;

    public Patient() {}

    public Patient(int patientId, String name, String phone, String gender, String address, int age, LocalDate dob) {

        this.patientId = patientId;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.dob = dob;

    }
    public int getId() {

        return patientId;

    }

    public int getPatientId() { return patientId; }

    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public LocalDate getDob() { return dob; }

    public void setDob(LocalDate dob) { this.dob = dob; }


    // ✅ VERY IMPORTANT FIX
    @Override
    public String toString() {


return name + " (ID: " + patientId + ")";

    }

}
