package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.repository.AppointmentRepositoryImpl;

import java.util.List;

public class AppointmentService {
    private final AppointmentRepositoryImpl appointmentRepository;
    public AppointmentService(AppointmentRepositoryImpl appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    public void createAppointment(Appointment appointment) {
        appointmentRepository.createDoctor(appointment);
    }
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.getAppointmentById(id);
    }
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.updateAppointment(appointment);
    }
    public void deleteAppointmentById(int id) {
        appointmentRepository.deleteAppointmentById(id);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }
}
