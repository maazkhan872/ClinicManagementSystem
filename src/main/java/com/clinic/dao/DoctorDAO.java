
package com.clinic.dao;

import com.clinic.models.Doctor;
import java.util.List;
import java.util.ArrayList;

public class DoctorDAO {

    // Example: fetch all doctors from database
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        
        // Hardcoded example data (replace with DB query)
        doctors.add(new Doctor(1, "Dr. Ali"));
        doctors.add(new Doctor(2, "Dr. Sara"));
        doctors.add(new Doctor(3, "Dr. Maaz"));
        
        return doctors;
    }
}
