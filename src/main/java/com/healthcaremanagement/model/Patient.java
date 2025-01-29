package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "Patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientID")
    private int patientId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "DateOfBirth")
    private String dateOfBirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;
//    @OneToMany(mappedBy = "Patients",cascade = CascadeType.ALL)
//private List<Appointment> appointments;

    public Patient() {
    }


}
