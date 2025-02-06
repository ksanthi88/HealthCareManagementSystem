package com.healthcaremanagement;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.repository.AppointmentRepositoryImpl;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;
import com.healthcaremanagement.repository.PatientRepositoryImpl;
import com.healthcaremanagement.service.AppointmentService;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.PatientService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest
{
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
  @BeforeEach
  void setUp()
  {
      sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      AppointmentRepositoryImpl appointmentRepository=new AppointmentRepositoryImpl(sessionFactory);
      DoctorRepositoryImpl doctorRepository=new DoctorRepositoryImpl(sessionFactory);
      PatientRepositoryImpl patientRepository=new PatientRepositoryImpl(sessionFactory);
      doctorService=new DoctorService(doctorRepository);
      appointmentService=new AppointmentService(appointmentRepository);
      patientService=new PatientService(patientRepository);
  }
  @AfterEach
    void tearDown()
  {
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
    void testCreateAppointment(){
      //Create and persist doctor
      Doctor doctor=new Doctor();
      doctor.setFirstName("Maddy");
      doctor.setLastName("Bob");
      doctor.setEmail("maddy.bob@gmail.com");
      doctor.setSpecialty("eye");
      doctorService.createDoctor(doctor);
      session.flush();
      //Create and persist Patient
      Patient patient=new Patient();
      patient.setFirstName("Bob");
      patient.setLastName("don");
      patient.setEmail("bob@gmail.com");
      patient.setDateOfBirth(LocalDate.parse("1988-07-12"));
      patientService.createPatient(patient);
      session.flush();
      Appointment appointment=new Appointment();
      appointment.setDoctor(doctor);
      appointment.setPatient(patient);
      appointment.setNotes("fever");
      LocalDateTime appointmentDate = LocalDateTime.parse("2025-01-28T12:00:00");
      appointment.setAppointmentDate(appointmentDate);
      appointmentService.createAppointment(appointment);
      Appointment fetched=appointmentService.getAppointmentById(appointment.getAppointmentId());
      //Assertion
      assertNotNull(fetched);
      assertEquals(appointment.getAppointmentId(), fetched.getAppointmentId());

  }
  @Test
    void testGetAppointmentById(){
      Doctor doctor=new Doctor();
      doctor.setFirstName("Maddy");
      doctor.setLastName("Bob");
      doctor.setSpecialty("Ortho");
      doctor.setEmail("bob.bob@gmail.com");
      doctorService.createDoctor(doctor);
      session.flush();
      Patient patient=new Patient();
      patient.setFirstName("Vicor");
      patient.setLastName("don");
      patient.setEmail("victoe@gmail.com");
      patient.setDateOfBirth(LocalDate.parse("1988-07-13"));
      patientService.createPatient(patient);
      session.flush();
      Appointment appointment=new Appointment();
      appointment.setDoctor(doctor);
      appointment.setPatient(patient);
      appointment.setNotes("take vitamind");
      LocalDateTime appointmentDate = LocalDateTime.parse("2025-01-30T12:00:00");
      appointment.setAppointmentDate(appointmentDate);
      appointmentService.createAppointment(appointment);
      session.flush();
      Appointment fetched=appointmentService.getAppointmentById(appointment.getAppointmentId());
      // Assert: Verify the appointment exists and matches expected data
      assertNotNull(fetched);
      assertEquals(appointment.getAppointmentId(), fetched.getAppointmentId());
      assertEquals(appointment.getDoctor(), fetched.getDoctor());
      assertEquals("take vitamind", fetched.getNotes());

  }
    @Test
    public void testGetAllAppointments() {
        // Arrange: Create multiple appointments
        Patient patient1 = new Patient();
        patient1.setFirstName("Alice");
        patient1.setLastName("Johnson");
        patient1.setEmail("alice@gmail.com");
        patient1.setDateOfBirth(LocalDate.parse("1988-08-13"));
        patientService.createPatient(patient1);
        session.flush();

        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Bob");
        doctor1.setLastName("Smith");
        doctor1.setEmail("bob@gmail.com");
        doctor1.setSpecialty("ENT");
        doctorService.createDoctor(doctor1);
        session.flush();

        Appointment appointment1 = new Appointment();
        appointment1.setPatient(patient1);
        appointment1.setDoctor(doctor1);
        LocalDateTime appointmentDate1 = LocalDateTime.parse("2025-01-10T12:00:00");
        appointment1.setAppointmentDate(appointmentDate1);
        appointment1.setNotes("Ear Checkup");
        appointmentService.createAppointment(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setPatient(patient1);
        appointment2.setDoctor(doctor1);
        LocalDateTime appointmentDate = LocalDateTime.parse("2025-02-10T12:00:00");
        appointment2.setAppointmentDate(appointmentDate);
        appointment2.setNotes("Follow-up");
        appointmentService.createAppointment(appointment2);

        // Act: Retrieve all appointments
        List<Appointment> appointments = appointmentService.getAllAppointments();

        // Assert: Check that appointments were retrieved and match expectations
        assertNotNull(appointments);
        assertTrue(appointments.size() >= 2, "There should be at least two appointments in the database.");
    }
    @Test
    public void testUpdateAppointment() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Alice");
        patient1.setLastName("Johnson");
        patient1.setEmail("aja@gmail.com");
        patient1.setDateOfBirth(LocalDate.parse("1988-09-13"));
        patientService.createPatient(patient1);
        session.flush();

        Doctor doctor = new Doctor();
        doctor.setFirstName("Jane");
        doctor.setLastName("Smith");
        doctor.setEmail("jane@gmail.com");
        doctor.setSpecialty("cardio");
        doctorService.createDoctor(doctor);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient1);
        appointment.setDoctor(doctor);
        LocalDateTime appointmentDate = LocalDateTime.parse("2025-01-10T12:00:00");
        appointment.setAppointmentDate(appointmentDate);
        appointment.setNotes("Initial notes");
        appointmentService.createAppointment(appointment);
        session.flush();
        appointment.setNotes("Updated notes");
        appointmentService.updateAppointment(appointment);

        Appointment updatedAppointment = appointmentService.getAppointmentById(appointment.getAppointmentId());
        assertEquals("Updated notes", updatedAppointment.getNotes());
    }
    @Test
    public void testDeleteAppointment() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Alice");
        patient1.setLastName("Johnson");
        patient1.setEmail("varu@gmail.com");
        patient1.setDateOfBirth(LocalDate.parse("1977-09-13"));
        patientService.createPatient(patient1);
        session.flush();

        Doctor doctor = new Doctor();
        doctor.setFirstName("Jane");
        doctor.setLastName("Smith");
        doctor.setEmail("smith@gmail.com");
        doctor.setSpecialty("neuro");
        doctorService.createDoctor(doctor);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient1);
        appointment.setDoctor(doctor);
        LocalDateTime appointmentDate = LocalDateTime.parse("2025-01-11T12:00:00");
        appointment.setAppointmentDate(appointmentDate);
        appointment.setNotes("To be deleted");

        appointmentService.createAppointment(appointment);
        int id = appointment.getAppointmentId();
        appointmentService.deleteAppointmentById(id);

        assertNull(appointmentService.getAppointmentById(id));
    }

    @ParameterizedTest
    @ValueSource(strings={"2025-02-10T10:00:00", "2025-03-15T15:30:00", "2025-04-20T09:45:00"})
    public void testCreateAppointmentWithDifferentDates(String date) {
      LocalDateTime dateTime = LocalDateTime.parse(date);
        Patient patient1 = new Patient();
        patient1.setFirstName("Alice");
        patient1.setLastName("mike");
        patient1.setEmail("mike@gmail.com");
        patient1.setDateOfBirth(LocalDate.parse("1978-09-13"));
        patientService.createPatient(patient1);
        session.flush();

        Doctor doctor = new Doctor();
        doctor.setFirstName("Jane");
        doctor.setLastName("Smith");
        doctor.setEmail("smithjjj@gmail.com");
        doctor.setSpecialty("neuro1");
        doctorService.createDoctor(doctor);
        Appointment appointment = new Appointment();
        appointment.setPatient(patient1);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dateTime);
        appointment.setNotes("Checkup");

        appointmentService.createAppointment(appointment);
        assertNotNull(appointment.getAppointmentId());
        assertEquals(dateTime, appointment.getAppointmentDate());
    }

}
