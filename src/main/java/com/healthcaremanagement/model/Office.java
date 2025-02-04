package com.healthcaremanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
@Table(name="Offices")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OfficeId")
    private int officeId;
    @Column(name="Location")
    String location;
    @Column(name="Phone")
    String phone;

    @OneToOne
    @JoinColumn(name="DoctorID")
    private Doctor doctor;
}
