package com.healthcaremanagement.runner;

import com.healthcaremanagement.model.Appointment;
import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.service.AppointmentService;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.PatientService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AppointmentManager {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private Scanner scanner ;
    public AppointmentManager(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, Scanner scanner) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.scanner = scanner;

    }


    public void manageAppointments() {
        while (true) {
            System.out.println("\n--- Appointment Management ---");
            System.out.println("1. Create Appointment");
            System.out.println("2. Read Appointment");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidChoice();
            switch (choice) {
                case 1 -> createAppointment();
                case 2 -> readAppointment();
                case 3 -> updateAppointment();
                case 4 -> deleteAppointment();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createAppointment() {
        Appointment newAppointment = new Appointment();
        Patient patient = getPatientById();
        Doctor doctor = getDoctorById();

        if (patient != null && doctor != null) {
            newAppointment.setPatient(patient);
            newAppointment.setDoctor(doctor);
            newAppointment.setAppointmentDate(getValidDate());
            System.out.print("Enter Appointment Notes: ");
            newAppointment.setNotes(scanner.nextLine());

            appointmentService.createAppointment(newAppointment);
            patientService.addDoctorToPatient(patient.getPatientId(), doctor.getDoctorId());
            doctorService.addPatientToDoctor(doctor.getDoctorId(),patient);
            System.out.println("Appointment created successfully.");
        }
    }
    private void readAppointment() {
        Appointment appointment = getAppointmentById();
        if (appointment != null) {
            displayAppointment(appointment);
        }
    }

    private void updateAppointment() {
        Appointment appointment = getAppointmentById();
        Patient patient = getPatientById();
        Doctor doctor = getDoctorById();
        if (appointment != null) {
            System.out.println("\n--- Update Appointment ---");

            System.out.print("Enter new Patient ID (Press Enter to keep current: " + appointment.getPatient().getPatientId() + "): ");
            String patientId = scanner.nextLine();
            Patient updatedPatient = patientService.getPatientById(Integer.parseInt(patientId));

            if (!patientId.isEmpty()) {
                if (updatedPatient != null) {
                    appointment.setPatient(updatedPatient);
                } else {
                    System.out.println("Patient not found, keeping existing patient.");
                }
            }

            System.out.print("Enter new Doctor ID (Press Enter to keep current: " + appointment.getDoctor().getDoctorId() + "): ");
            String doctorId = scanner.nextLine();
            Doctor updatedDoctor = doctorService.getDoctorById(Integer.parseInt(doctorId));

            if (!doctorId.isEmpty()) {
                if (updatedDoctor != null && updatedPatient != null) {
                    appointment.setDoctor(updatedDoctor);
                    if (!appointmentService.hasOtherAppointmentsBetween(
                            updatedDoctor.getDoctorId(), updatedPatient.getPatientId())) {
                        doctorService.removePatientFromDoctor(updatedDoctor.getDoctorId(), updatedPatient);
                        patientService.removeDoctorFromPatient(updatedPatient.getPatientId(), updatedDoctor);
                    }
                } else {
                    System.out.println("Doctor not found, keeping existing doctor.");
                }
            }

            System.out.print("Enter new Appointment Date (yyyy-MM-dd HH:mm, Press Enter to keep current: " + appointment.getAppointmentDate() + "): ");
            String appointmentDate = scanner.nextLine();
            if (!appointmentDate.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                appointment.setAppointmentDate(LocalDateTime.parse(appointmentDate, formatter));
            }

            System.out.print("Enter new Notes (Press Enter to keep current: " + appointment.getNotes() + "): ");
            String notes = scanner.nextLine();
            if (!notes.isEmpty()) appointment.setNotes(notes);

            appointmentService.updateAppointment(appointment);
            this.doctorService.addPatientToDoctor(doctor.getDoctorId(), patient);
            this.patientService.addDoctorToPatient(patient.getPatientId(), doctor.getDoctorId());
            System.out.println("Appointment updated successfully.");
        }
    }

    private void deleteAppointment() {
        Appointment appointment = getAppointmentById();
        if (appointment != null) {
            appointmentService.deleteAppointmentById(appointment.getAppointmentId());
            if (!appointmentService.hasOtherAppointmentsBetween(
                    appointment.getDoctor().getDoctorId(),appointment.getPatient().getPatientId())) {
                doctorService.removePatientFromDoctor(appointment.getDoctor().getDoctorId(), appointment.getPatient());
                patientService.removeDoctorFromPatient(appointment.getPatient().getPatientId(), appointment.getDoctor());
            }
            System.out.println("Appointment deleted successfully.");
        }
    }

    private Appointment getAppointmentById() {
        System.out.print("Enter Appointment ID: ");
        int id = getValidChoice();
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            System.out.println("Appointment not found.");
        }
        return appointment;
    }

    private LocalDateTime getValidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd HH:mm): ");
            try {
                return LocalDateTime.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter again.");
            }
        }
    }
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
    private void displayAppointment(Appointment appointment) {
        System.out.println("\n--- Appointment Details ---");
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Patient ID: " + appointment.getPatient().getPatientId());
        System.out.println("Doctor ID: " + appointment.getDoctor().getDoctorId());
        System.out.println("Appointment Date: " + appointment.getAppointmentDate());
        System.out.println("Notes: " + appointment.getNotes());
    }
    private Patient getPatientById() {
        System.out.print("Enter Patient ID: ");
        int id = getValidChoice();
        Patient patient = patientService.getPatientById(id);
        if (patient == null) System.out.println("Patient not found.");
        return patient;
    }
    private Doctor getDoctorById() {
        System.out.print("Enter Doctor ID: ");
        int id = getValidChoice();
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) System.out.println("Doctor not found.");
        return doctor;
    }
}
