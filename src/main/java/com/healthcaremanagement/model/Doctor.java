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
@ToString
@Table(name="Doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DoctorId")
    private int doctorId;
    @Column(name="FirstName")
    private String firstName;
    @Column(name="LastName")
    private String lastName;
    @Column(name="Specialty")
    private  String specialty;
    @Column(name="Email")
    private String email;
//    @OneToMany(mappedBy = "Doctors",cascade = CascadeType.ALL)//one doctor having multiple appointments
//    private List<Appointment> appointmentList= new ArrayList<>();

}
