package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="Appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AppointmentId")
    private int appointmentId;
@ManyToOne
    @JoinColumn(name="PatientId",nullable = false)//foreign key from patient
    private Patient patient;
@ManyToOne
    @JoinColumn(name="DoctorId",nullable = false)//foreign Key from doctors
    private Doctor doctor;

   @Column(name="AppointmentDate",nullable = false)
    private LocalDateTime appointmentDate;
    @Column(name="Notes")
    private String notes;
}
