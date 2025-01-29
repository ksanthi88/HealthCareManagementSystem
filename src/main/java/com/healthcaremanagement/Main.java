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
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.cfg.Configuration;

import java.lang.runtime.SwitchBootstraps;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("patient.cfg.xml").buildSessionFactory();
        //Intialize Patinet service class
        PatientRepositoryImpl patientRepository = new PatientRepositoryImpl(sessionFactory);
        PatientService patientService = new PatientService(patientRepository);
        //Intialize Doctor service class
        DoctorRepositoryImpl doctorRepository = new DoctorRepositoryImpl(sessionFactory);
        DoctorService doctorService = new DoctorService(doctorRepository);
        //Intialize Appointment service class
        AppointmentRepositoryImpl appointmentRepository = new AppointmentRepositoryImpl(sessionFactory);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        //intialize scanner
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to HealthCare Management System");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Appointments");
            System.out.println("Please enter your choice");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    manageDoctors(doctorService, scanner);
                    break;
                case 2:
                    managePatients(patientService, scanner);
                    break;
                case 3:
                    manageAppointments(appointmentService, scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using HealthCare Management System");
                    scanner.close();
                    sessionFactory.close();
                    return;
                default:
                System.out.println("Invalid choice");
            }

        }
    }

    private static void manageDoctors(DoctorService doctorService, Scanner scanner) {
        while (true) {
            System.out.println("1. Create Doctor");
            System.out.println("2. Read Doctor");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5.Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        //create a doctor
                        // Application calls the service layer, not the repository directly
                        Doctor newDoctor = new Doctor();
                        System.out.print("Enter First Name: ");
                        newDoctor.setFirstName(scanner.nextLine());
                        System.out.print("Enter Last Name: ");
                        newDoctor.setLastName(scanner.nextLine());
                        System.out.print("Enter Specialty: ");
                        newDoctor.setSpecialty(scanner.nextLine());
                        System.out.print("Enter email: ");
                        newDoctor.setEmail(scanner.nextLine());
                        doctorService.createDoctor(newDoctor);  // Use service here
                        System.out.println("Doctor created successfully.");
                        break;
                    case 2:
                        //read a doctor
                        // Application calls the service layer, not the repository directly
                        System.out.print("Enter Doctor ID: ");
                        int doctorId = scanner.nextInt();
                        Doctor doctor = doctorService.getDoctorById(doctorId);  // Use service here
                        if (doctor != null) {
                            System.out.println("Doctor ID: " + doctor.getDoctorId());
                            System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
                            System.out.println("Specialty: " + doctor.getSpecialty());
                            System.out.println("Email: " + doctor.getEmail());
                        } else {
                            System.out.println("Doctor not found.");
                        }
                        break;
                    case 3:
                        //update a doctor
                        // Application calls the service layer, not the repository directly
                        System.out.print("Enter Doctor ID: ");
                        doctorId = scanner.nextInt();
                        scanner.nextLine();  // consume newline
                        doctor = doctorService.getDoctorById(doctorId);  // Use service here
                        if (doctor != null) {
                            System.out.print("Enter new first name: ");
                            doctor.setFirstName(scanner.nextLine());
                            System.out.print("Enter new last name: ");
                            doctor.setLastName(scanner.nextLine());
                            System.out.print("Enter New Specialty: ");
                            doctor.setSpecialty(scanner.nextLine());
                            System.out.print("Enter new email: ");
                            doctor.setEmail(scanner.nextLine());
                            doctorService.updateDoctor(doctor);  // Use service here
                            System.out.println("Doctor updated successfully.");
                        } else {
                            System.out.println("Doctor not found.");
                        }
                        break;
                    case 4:
                        // Application calls the service layer, not the repository directly
                        System.out.print("Enter Doctor ID: ");
                        doctorId = scanner.nextInt();
                        doctor = doctorService.getDoctorById(doctorId);
                        if (doctor != null) {
                            doctorService.deleteDoctorById(doctorId);
                        } else {
                            System.out.println("Patient not found.");
                        }
                        System.out.println("Doctor deleted successfully.");
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } finally {
                scanner.close();
            }
        }
    }

    private static void managePatients(PatientService patientService, Scanner scanner) {
        while (true) {
            System.out.println("1. Create Patient");
            System.out.println("2. Read Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5.Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                        Patient newPatient = new Patient();
                        System.out.println("Enter New Patient First Name: ");
                        newPatient.setFirstName(scanner.nextLine());
                        System.out.println("Enter New Patient Last Name: ");
                        newPatient.setLastName(scanner.nextLine());
                        System.out.println("Enter New Patient Date of Birth:[yyyy-MM-dd]: ");
                        newPatient.setDateOfBirth(scanner.nextLine());
                        System.out.println("Enter New Patient Email: ");
                        newPatient.setEmail(scanner.nextLine());
                        System.out.println("Enter New Patient Phone Number: ");
                        newPatient.setPhoneNumber(scanner.nextLine());
                        patientService.createPatient(newPatient);
                        System.out.println("Patinet created successfully.");
                        break;
                    case 2:
                        System.out.print("Enter Patient ID: ");
                        int patientId = scanner.nextInt();
                        scanner.nextLine();
                        Patient patient = patientService.getPatientById(patientId);
                        if (patient != null) {
                            System.out.println("Patient:  ID" + patient.getPatientId());
                            System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                            System.out.println("DateOfBirth: " + patient.getDateOfBirth());
                            System.out.println("Email: " + patient.getEmail());
                            System.out.println("PhoneNumber: " + patient.getPhoneNumber());
                        } else {
                            System.out.println("Patient not found.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter Patient ID: ");
                        patientId = scanner.nextInt();
                        scanner.nextLine();
                        patient = patientService.getPatientById(patientId);
                        if (patient != null) {
                            System.out.println("Enter new Patient first name: ");
                            patient.setFirstName(scanner.nextLine());
                            System.out.println("Enter new patient last name: ");
                            patient.setLastName(scanner.nextLine());
                            System.out.println("Enter new Patient Date of Birth: ");
                            patient.setDateOfBirth(scanner.nextLine());
                            System.out.println("Enter new patient email: ");
                            patient.setEmail(scanner.nextLine());
                            System.out.println("Enter New Patient PhoneNumber");
                            patient.setPhoneNumber(scanner.nextLine());
                            patientService.updatePatient(patient);
                            System.out.println("Patient updated successfully.");

                        } else {
                            System.out.println("Patient not found.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter Patient ID: ");
                        patientId = scanner.nextInt();
                        patient = patientService.getPatientById(patientId);
                        if (patient != null) {
                            patientService.deletePatient(patientId);
                            System.out.println("Patient deleted successfully.");
                        } else {
                            System.out.println("Patient not found.");
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } finally {
                scanner.close();
            }
        }
    }

    private static void manageAppointments(AppointmentService appointmentService, Scanner scanner) {
        while (true) {
            System.out.println("1. Create Appointment");
            System.out.println("2. Read Appointment");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1://create appointment
                    Appointment newAppointment = new Appointment();
                    System.out.println("Enter New Appointment Patient ID: ");
                    newAppointment.setPatientId(scanner.nextInt());
                    System.out.println("Enter New Appointment Doctor ID: ");
                    newAppointment.setDoctorId(scanner.nextInt());
                    System.out.println("Enter New Appointment Date:[yyyy-MM-dd]: ");
                    newAppointment.setAppointmentDate(scanner.nextLine());
                    System.out.println("Enter New Appointment Notes: ");
                    newAppointment.setNotes(scanner.nextLine());
                    appointmentService.createAppointment(newAppointment);
                    System.out.println("New Appointment created successfully.");
                    break;
                case 2:  //Read Appointment
                    System.out.print("Enter Appointment ID: ");
                    int appointmentId = scanner.nextInt();
                    scanner.nextLine();
                    Appointment appointment = appointmentService.getAppointmentById(appointmentId);
                    if (appointment != null) {
                        System.out.println("Appointment ID: " + appointment.getAppointmentId());
                        System.out.println("Patient ID: " + appointment.getPatientId());
                        System.out.println("Doctor ID: " + appointment.getDoctorId());
                        System.out.println("AppointmentDate " + appointment.getAppointmentDate());
                        System.out.println("AppointmentNotes " + appointment.getNotes());
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
                case 3://Update Appointment
                    System.out.print("Enter Appointment ID: ");
                    appointmentId = scanner.nextInt();
                    scanner.nextLine();
                    appointment = appointmentService.getAppointmentById(appointmentId);
                    if (appointment != null) {
                        System.out.println("Enter new Appointment patient Id: ");
                        appointment.setPatientId(scanner.nextInt());
                        System.out.println("Enter new Appointment doctor Id: ");
                        appointment.setDoctorId(scanner.nextInt());
                        System.out.println("Enter new Appointment AppointmentDate[yyyy-MM-dd]: ");
                        appointment.setAppointmentDate(scanner.nextLine());
                        System.out.println("Enter new Appointment Notes: ");
                        appointment.setNotes(scanner.nextLine());
                        appointmentService.updateAppointment(appointment);
                        System.out.println("Appointment updated successfully.");

                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Appointment ID: ");
                    appointmentId = scanner.nextInt();
                    appointment = appointmentService.getAppointmentById(appointmentId);
                    if (appointment != null) {
                        appointmentService.deleteAppointmentById(appointmentId);
                        System.out.println("Appointment deleted successfully.");
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}