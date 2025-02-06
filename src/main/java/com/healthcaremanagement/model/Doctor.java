package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude ={ "patients","appointmentList"})
@EqualsAndHashCode(exclude = "appointmentList")
@Table(name="Doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DoctorId")
    private int doctorId;
    @Column(name="FirstName",nullable=false)
    private String firstName;
    @Column(name="LastName",nullable=false)
    private String lastName;
    @Column(name="Specialty",nullable=false)
    private  String specialty;
    @Column(name="Email",nullable = false,unique = true)
    private String email;

   @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,orphanRemoval = true)//one doctor having multiple appointments
 private List<Appointment> appointmentList= new ArrayList<>();

    @OneToOne(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Office office;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Doctor_Patient",
            joinColumns = @JoinColumn(name = "DoctorID"),
            inverseJoinColumns = @JoinColumn(name = "PatientID")
    )
   private List<Patient> patients;
}
