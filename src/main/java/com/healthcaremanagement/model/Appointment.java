package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name="PatientId")//foreign key from patient
    private int patientId;
    @JoinColumn(name="DoctorId")//foreign Key from doctors
    private int doctorId;
   @Column(name="AppointmentDate")
    private String appointmentDate;
    @Column(name="Notes")
    private String notes;
}
