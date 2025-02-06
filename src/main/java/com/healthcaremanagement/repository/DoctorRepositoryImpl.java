package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.model.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorRepositoryImpl {
    private  final SessionFactory sessionFactory;

    public DoctorRepositoryImpl(SessionFactory sessionFactory ){

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
//    public void deleteDoctorById(int id) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            Doctor doctor = session.get(Doctor.class, id);
//
//            if (doctor == null) {
//                transaction.rollback();
//                return;
//            }
//
//            // Remove references from related tables
//            if (doctor.getOffice() != null) {
//                Office office = doctor.getOffice();
//                office.setDoctor(null);
//                session.merge(office);
//            }
//
//            for (Patient patient : doctor.getPatients()) {
//                patient.getDoctors().remove(doctor);
//                session.merge(patient);
//            }
//
//            session.remove(doctor);
//            transaction.commit();
//        }
//    }
    public void deleteDoctorById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, id);

            if (doctor != null) {

                if (doctor.getOffice() != null) {
                    doctor.getOffice().setDoctor(null);
                    session.merge(doctor.getOffice());
                }

                session.remove(doctor);
            }

            transaction.commit();
        }

    }
    //listing all doctors
    public List<Doctor> getAllDoctors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Doctor",Doctor.class).list();
        }
    }
    public void addPatientToDoctor(int doctorId, Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null && !doctor.getPatients().contains(patient)) {
                doctor.getPatients().add(patient);
                session.merge(doctor);
            }
            transaction.commit();
        }
    }
    public void removePatientFromDoctor(int doctorId, Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null && doctor.getPatients().contains(patient)) {
                doctor.getPatients().remove(patient);
                session.merge(doctor);
            }
            transaction.commit();
        }
    }

}