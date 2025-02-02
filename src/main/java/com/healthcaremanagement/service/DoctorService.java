package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;

import java.util.List;

public class DoctorService {
    private final DoctorRepositoryImpl doctorRepositoryImpl;
    public DoctorService(DoctorRepositoryImpl doctorRepositoryImpl) {
        this.doctorRepositoryImpl = doctorRepositoryImpl;
    }
    public void createDoctor(Doctor doctor) {
        doctorRepositoryImpl.createDoctor(doctor);
    }
    public Doctor getDoctorById(int id) {
        return doctorRepositoryImpl.getDoctorById(id);
    }
    public void updateDoctor(Doctor doctor) {
        doctorRepositoryImpl.updateDoctor(doctor);
    }
    public void deleteDoctorById(int id) {
        doctorRepositoryImpl.deleteDoctorById(id);
    }
   public List<Doctor> getAllDoctors() {
        return doctorRepositoryImpl.getAllDoctors();
   }
   public void addPatientToDoctor(int doctorId, Patient patient){
        doctorRepositoryImpl.addPatientToDoctor(doctorId,patient);
   }
   public void removePatientFromDoctor(int doctorId, Patient patient){
        doctorRepositoryImpl.removePatientFromDoctor(doctorId,patient);
   }
}
