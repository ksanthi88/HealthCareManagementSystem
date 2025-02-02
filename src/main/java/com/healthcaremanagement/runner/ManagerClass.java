package com.healthcaremanagement.runner;

import com.healthcaremanagement.repository.AppointmentRepositoryImpl;
import com.healthcaremanagement.repository.DoctorRepositoryImpl;
import com.healthcaremanagement.repository.OfficeRepositoryImpl;
import com.healthcaremanagement.repository.PatientRepositoryImpl;
import com.healthcaremanagement.service.AppointmentService;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.OfficeService;
import com.healthcaremanagement.service.PatientService;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class ManagerClass {
    private final Scanner scanner;
    private final DoctorManager doctorManager;
    private final PatientManager patientManager;
    private final AppointmentManager appointmentManager;
    private final OfficeManager officeManager;
    private final SessionFactory sessionFactory;

    public ManagerClass(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.scanner = new Scanner(System.in);

        this.doctorManager = new DoctorManager(new DoctorService(new DoctorRepositoryImpl(sessionFactory)), scanner);
        this.patientManager = new PatientManager(new PatientService(new PatientRepositoryImpl(sessionFactory)), scanner);
        this.appointmentManager = new AppointmentManager(
                new AppointmentService(new AppointmentRepositoryImpl(sessionFactory)),
                new DoctorService(new DoctorRepositoryImpl(sessionFactory)),
                new PatientService(new PatientRepositoryImpl(sessionFactory)),
                scanner
        );
        this.officeManager = new OfficeManager(new OfficeService(new OfficeRepositoryImpl(sessionFactory)), scanner);
    }
    public void healthRunner() {
        while (true) {
            System.out.println("\n--- HealthCare Management System ---");
            System.out.println("1. Manage Doctors");
            System.out.println("2. Manage Patients");
            System.out.println("3. Manage Appointments");
            System.out.println("4. Manage Offices");
            System.out.println("5. Exit");
            System.out.print("Please enter your choice: ");

            int choice = getValidChoice();
            switch (choice) {
                case 1 -> {
                    doctorManager.manageDoctors();
                    System.out.println("ManageDoctor session ended..");
                }
                case 2 -> patientManager.managePatients();
                case 3 -> appointmentManager.manageAppointments();
                case 4 -> officeManager.manageOffices();
                case 5 -> {
                    System.out.println("Thank you for using HealthCare Management System");
                    scanner.close();
                    sessionFactory.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    //helper method to give valid input
    private int getValidChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return choice;
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }
    }
}
