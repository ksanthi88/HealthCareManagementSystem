package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AppointmentRepositoryImpl {
    private  final SessionFactory sessionFactory;

    public AppointmentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //Create Appointment
    public void createDoctor(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(appointment);
            transaction.commit();
        }

    }
    //Reading Appointment
    public Appointment getAppointmentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Appointment.class, id);

        }
    }
    //Update Appointment
    public void updateAppointment(Appointment appointment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(appointment);
            transaction.commit();
        }
    }
    //Delete Appointment
    public void deleteAppointmentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class, id);
            session.remove(appointment);
            transaction.commit();
        }
    }
    //listing all Appointment
    public List<Appointment> getAllAppointments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Appointment",Appointment.class).list();
        }
    }

}
