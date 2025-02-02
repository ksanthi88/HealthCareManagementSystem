package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@Table(name = "Patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientID")
    private int patientId;

    @Column(name = "FirstName",nullable = false)
    private String firstName;

    @Column(name = "LastName",nullable = false)
    private String lastName;

    @Column(name = "DateOfBirth",nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "Email",unique = true)
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;
 @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,orphanRemoval = true)
private List<Appointment> appointments=new ArrayList<>();

 @ManyToMany(mappedBy = "patients")
 private List<Doctor> doctors=new ArrayList<>();

    public Patient() {
    }


}
