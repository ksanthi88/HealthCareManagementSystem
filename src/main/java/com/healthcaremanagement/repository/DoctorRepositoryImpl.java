package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Doctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorRepositoryImpl {
    private  final SessionFactory sessionFactory;

    public DoctorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
//Create doctor
    public void createDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(doctor);
            transaction.commit();
        }

    }
    //Reading doctor
    public Doctor getDoctorById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Doctor.class, id);

    }
}
//Update doctor
    public void updateDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(doctor);
            transaction.commit();
        }
    }
    //Delete Doctor
    public void deleteDoctorById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, id);
            session.remove(doctor);
            transaction.commit();
        }
    }
    //listing all doctors
    public List<Doctor> getAllDoctors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Doctor",Doctor.class).list();
        }
    }

}