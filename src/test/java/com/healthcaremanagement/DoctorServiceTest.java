package com.healthcaremanagement;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;
import com.healthcaremanagement.service.DoctorService;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.print.Doc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorServiceTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        DoctorRepositoryImpl doctorRepository = new DoctorRepositoryImpl(sessionFactory);
        doctorService = new DoctorService(doctorRepository);

    }

    @AfterEach
    public void tearDown() {
        if (transaction != null && transaction.getStatus().canRollback()) {
            transaction.rollback(); // Rollback only if possible
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @Test
    public void toCreateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@doe.com");
        doctor.setSpecialty("family");
        doctorService.createDoctor(doctor);
        assertNotNull(doctor.getFirstName());
    }
    @Test
    public void testGetDoctorById()
    {
        Doctor doctor=new Doctor();
        doctor.setFirstName("Varun");
        doctor.setLastName("Doe");
        doctor.setEmail("varun@doe.com");
        doctor.setSpecialty("Ortho");
        doctorService.createDoctor(doctor);
        Doctor fetchedDoctor=doctorService.getDoctorById(doctor.getDoctorId());
        assertNotNull(fetchedDoctor,"Doctor should exist in the database");
        assertEquals(fetchedDoctor.getDoctorId(),doctor.getDoctorId());
        assertEquals(doctor.getFirstName(), fetchedDoctor.getFirstName());
       assertEquals("Ortho", fetchedDoctor.getSpecialty());
    }
    @Test
    public void testGetAllDoctors()
    {
       Doctor doctor1=new Doctor();
        doctor1.setFirstName("John");
        doctor1.setLastName("Doe");
        doctor1.setEmail("john@doe.com");
        doctor1.setSpecialty("family");
        doctorService.createDoctor(doctor1);

        Doctor doctor2=new Doctor();
        doctor2.setFirstName("Varun");
        doctor2.setLastName("Doe");
        doctor2.setEmail("varun@doe.com");
        doctor2.setSpecialty("Ortho");
        doctorService.createDoctor(doctor2);
        List<Doctor> doctors=doctorService.getAllDoctors();
        assertNotNull(doctors);
        assertEquals(2, doctors.size());
        assertTrue(doctors.size()>=2,"There should be at least 2 Doctors in the database");
    }

    @Test
    public void testUpdateDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@doe.com");
        doctor.setSpecialty("family");
        doctorService.createDoctor(doctor);

        Doctor updatedDoctor = doctorService.getDoctorById(doctor.getDoctorId());
        assertNotNull(updatedDoctor);
        updatedDoctor.setSpecialty("Neurology");
        doctorService.updateDoctor(updatedDoctor);
        Doctor updatedDoctor1 = doctorService.getDoctorById(1);
        assertNotNull(updatedDoctor);
        assertEquals("Neurology", updatedDoctor.getSpecialty());
    }
    @Test
    public void testDeleteDoctorById() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john@doe.com");
        doctor.setSpecialty("family");
        doctorService.createDoctor(doctor);
        doctorService.deleteDoctorById(doctor.getDoctorId());
        Doctor deletedDoctor = doctorService.getDoctorById(doctor.getDoctorId());
        assertNull(deletedDoctor);


    }
    @ParameterizedTest
    @ValueSource(strings={"Family","Cardio","Eye","MRI"})
    public void testCreateDoctorWithDifferentSpecialty(String specialty) {
Doctor doctor = new Doctor();
doctor.setFirstName("John");
doctor.setLastName("Doe");
doctor.setEmail("john@doe.com");
doctor.setSpecialty(specialty);
doctorService.createDoctor(doctor);
assertNotNull(doctor.getDoctorId());
assertEquals(specialty, doctor.getSpecialty());
    }

}