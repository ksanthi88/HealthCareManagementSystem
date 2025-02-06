package com.healthcaremanagement;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;
import com.healthcaremanagement.repository.OfficeRepositoryImpl;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.OfficeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfficeServiceTest {
    private  SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private OfficeService officeService;
    private OfficeRepositoryImpl officeRepository;
    private DoctorService doctorService;
    private DoctorRepositoryImpl doctorRepository;

@BeforeEach
    public void setUp() {
     sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
   session = sessionFactory.openSession();
    transaction= session.beginTransaction();
    officeRepository=new OfficeRepositoryImpl(sessionFactory);
    doctorRepository=new DoctorRepositoryImpl(sessionFactory);
    officeService=new OfficeService(officeRepository);
    doctorService=new DoctorService(doctorRepository);
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
public void testCreateOffice() {
    // Step 1: Create and persist a Doctor entity
    Doctor doctor = new Doctor();
    doctor.setFirstName("Dr. Smith");
    doctor.setLastName("Smith");
    doctor.setSpecialty("Cardiology");
    doctor.setEmail("smith@gmail.com");
    doctorService.createDoctor(doctor);

    // Step 2: Create an Office entity and associate it with the saved doctor
    Office office = new Office();
    office.setLocation("123 Main St");
    office.setPhone("123-456-7890");
    office.setDoctor(doctor);

    // Step 3: Persist the Office entity
    officeService.createOffice(office);

    // Step 4: Retrieve the Office entity from the database
    Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());

    assertNotNull(fetchedOffice,"Office should exist in the database.");
    assertEquals("123 Main St",fetchedOffice.getLocation());
    assertEquals("123-456-7890",fetchedOffice.getPhone());
}
@Test
public void testGetOfficeById()
{
    Doctor doctor = new Doctor();
    doctor.setFirstName("Dr. Smith");
    doctor.setLastName("Smith");
    doctor.setSpecialty("Cardiology");
    doctor.setEmail("smith@gmail.com");
    doctorService.createDoctor(doctor);
    Office office = new Office();
    office.setLocation("Lakeville");
    office.setPhone("123-456-7890");
    office.setDoctor(doctor);
    officeService.createOffice(office);
    Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());
    assertNotNull(fetchedOffice,"Office should exist in the database.");
    assertEquals("Lakeville",fetchedOffice.getLocation());
    assertEquals("123-456-7890",fetchedOffice.getPhone());
}
@Test
public void testUpdateOffice()
{
    Doctor doctor = new Doctor();
    doctor.setFirstName("Vigu");
    doctor.setLastName("Smith");
    doctor.setSpecialty("Cardiology");
    doctor.setEmail("Vigu@gmail.com");
    doctorService.createDoctor(doctor);
    Office office = new Office();
    office.setLocation("Mountain View");
    office.setPhone("99786");
    office.setDoctor(doctor);
    officeService.createOffice(office);
    office.setLocation("Mount view");
    office.setPhone("612-567-078");
    officeService.updateOffice(office);
    Office updatedOffice = officeService.getOfficeById(office.getOfficeId());
    assertNotNull(updatedOffice,"Office should exist in the database.");
    assertEquals("Mount view",updatedOffice.getLocation(),"Location should be updated.");
    assertEquals("612-567-078",updatedOffice.getPhone(),"Phone should be updated.");
}
@Test
public void testDeleteOfficeById()
{
    Doctor doctor = new Doctor();
    doctor.setFirstName("Vigu");
    doctor.setLastName("Smith");
    doctor.setSpecialty("Cardiology");
    doctor.setEmail("Vigu@gmail.com");
    doctorService.createDoctor(doctor);
    session.flush();
    Office office = new Office();
    office.setLocation("Mountain View");
    office.setPhone("99786");
    office.setDoctor(doctor);
    officeService.createOffice(office);
    session.flush();
    officeService.deleteOfficeById(office.getOfficeId());
    Office deletedOffice = officeService.getOfficeById(office.getOfficeId());
    assertNotNull(office,"Office should exist in the database.");
    assertNull(deletedOffice,"Office should not exist in the database.");
}
}
