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
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfficeServiceTestAll {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    private OfficeService officeService;
    private DoctorService doctorService;

    @BeforeAll
    void setUp() {
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        OfficeRepositoryImpl officeRepository = new OfficeRepositoryImpl(sessionFactory);
        officeService = new OfficeService(officeRepository);
        DoctorRepositoryImpl doctorRepository = new DoctorRepositoryImpl(sessionFactory);
        doctorService = new DoctorService(doctorRepository);
    }

    @AfterAll
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
    @Order(1)
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

        assertNotNull(fetchedOffice, "Office should exist in the database.");
        assertEquals("123 Main St", fetchedOffice.getLocation());
        assertEquals("123-456-7890", fetchedOffice.getPhone());
    }

    @Test
    @Order(2)
    public void testGetOfficeById() {
        String uniqueEmail = "smith" + System.currentTimeMillis() + "@example.com";
        Doctor doctor = new Doctor();
        doctor.setFirstName("Dr. Smith");
        doctor.setLastName("Smith");
        doctor.setSpecialty("Cardiology");
        doctor.setEmail(uniqueEmail);
        doctorService.createDoctor(doctor);
        Office office = new Office();
        office.setLocation("Lakeville");
        office.setPhone("123-456-7890");
        office.setDoctor(doctor);
        officeService.createOffice(office);
        Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());
        assertNotNull(fetchedOffice, "Office should exist in the database.");
        assertEquals("Lakeville", fetchedOffice.getLocation());
        assertEquals("123-456-7890", fetchedOffice.getPhone());
    }

    @Test
    @Order(3)
    public void testUpdateOffice() {
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
        assertNotNull(updatedOffice, "Office should exist in the database.");
        assertEquals("Mount view", updatedOffice.getLocation(), "Location should be updated.");
        assertEquals("612-567-078", updatedOffice.getPhone(), "Phone should be updated.");
    }

    @Test
    @Order(4)
    public void testDeleteOfficeById() {
        String uniqueEmail = "vigu" + System.currentTimeMillis() + "@example.com";
        Doctor doctor = new Doctor();
        doctor.setFirstName("Vigu");
        doctor.setLastName("Smith");
        doctor.setSpecialty("Cardiology");
        doctor.setEmail(uniqueEmail);
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
        assertNotNull(office, "Office should exist in the database.");
        assertNull(deletedOffice, "Office should not exist in the database.");
    }
    @Test
    @Order(5)
public void testGetAllOffices() {
    String uniqueEmail = "smith" + System.currentTimeMillis() + "@example.com";
        Doctor doctor = new Doctor();
        doctor.setFirstName("Dr. Smith");
        doctor.setLastName("Smith");
        doctor.setSpecialty("Cardiology");
        doctor.setEmail(uniqueEmail);
        doctorService.createDoctor(doctor);
        Office office = new Office();
        office.setLocation("Lakeville");
        office.setPhone("123-456-7890");
        office.setDoctor(doctor);
        officeService.createOffice(office);
        Office fetchedOffice = officeService.getOfficeById(office.getOfficeId());
        assertNotNull(fetchedOffice, "Office should exist in the database.");
        assertEquals("Lakeville", fetchedOffice.getLocation());
}
    @ParameterizedTest
    @ValueSource(strings = {"Houston", "Boston", "Seattle", "San Diego"})
    @Order(6)
    public void testCreateOfficeWithDifferentLocations(String location) {
        String uniqueEmail = "greg.house" + System.currentTimeMillis() + "@example.com"; // Unique email

        // Create doctor
        Doctor doctor = new Doctor();
        doctor.setFirstName("Greg");
        doctor.setLastName("House");
        doctor.setSpecialty("Diagnostics");
        doctor.setEmail(uniqueEmail);
        doctorService.createDoctor(doctor);

        // Flush changes to DB
        session.flush();
        session.clear();

        // Create office
        Office office = new Office();
        office.setLocation(location);
        office.setPhone("999-999-9999");
        office.setDoctor(doctor);

        officeService.createOffice(office);

        // Retrieve office from DB
        Office retrievedOffice = officeService.getOfficeById(office.getOfficeId());

        // Assertions
        assertNotNull(retrievedOffice, "Office should be created.");
        assertEquals(location, retrievedOffice.getLocation(), "Office location should match.");
    }
}