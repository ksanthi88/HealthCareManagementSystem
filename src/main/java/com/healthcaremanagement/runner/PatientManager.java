package com.healthcaremanagement.runner;

import com.healthcaremanagement.model.Patient;
import com.healthcaremanagement.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class PatientManager {
    private PatientService patientService;
    private final Scanner scanner;
    public PatientManager(PatientService patientService, Scanner scanner) {
        this.patientService = patientService;
        this.scanner=scanner;
    }
    public void managePatients() {
        while (true) {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Create Patient");
            System.out.println("2. Read Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidChoice();
            switch (choice) {
                case 1 -> createPatient();
                case 2 -> {
                    Patient patient = getPatientById();
                    if (patient != null) displayPatient(patient);
                }
                case 3 -> {
                    Patient patient = getPatientById();
                    if (patient != null) updatePatient(patient);
                }
                case 4 -> {
                    Patient patient = getPatientById();
                    if (patient != null) {
                        patientService.deletePatient(patient.getPatientId());
                        System.out.println("Patient deleted successfully.");
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void createPatient() {
        Patient newPatient = new Patient();
        System.out.print("Enter First Name: ");
        newPatient.setFirstName(scanner.nextLine());
        System.out.print("Enter Last Name: ");
        newPatient.setLastName(scanner.nextLine());
        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        newPatient.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
        System.out.print("Enter Email: ");
        newPatient.setEmail(scanner.nextLine());
        System.out.print("Enter Phone Number: ");
        newPatient.setPhoneNumber(scanner.nextLine());
        patientService.createPatient(newPatient);
        System.out.println("Patient created successfully.");
    }
    private void updatePatient(Patient patient) {
        System.out.println("\n--- Update Patient ---");

        System.out.print("Enter new First Name (Press Enter to keep current: " + patient.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) patient.setFirstName(firstName);

        System.out.print("Enter new Last Name (Press Enter to keep current: " + patient.getLastName() + "): ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) patient.setLastName(lastName);

        System.out.print("Enter new Date of Birth (yyyy-MM-dd, Press Enter to keep current: " + patient.getDateOfBirth() + "): ");
        String dob = scanner.nextLine();
        if (!dob.isEmpty()) {
            try {
                patient.setDateOfBirth(LocalDate.parse(dob));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Keeping previous value.");
            }
        }

        System.out.print("Enter new Email (Press Enter to keep current: " + patient.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) patient.setEmail(email);

        System.out.print("Enter new Phone Number (Press Enter to keep current: " + patient.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.isEmpty()) patient.setPhoneNumber(phoneNumber);

        patientService.updatePatient(patient);
        System.out.println("Patient updated successfully.");
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
    //Helper method to getting patient id
    private Patient getPatientById() {
        System.out.print("Enter Patient ID: ");
        int id = getValidChoice();
        Patient patient = patientService.getPatientById(id);
        if (patient == null) System.out.println("Patient not found.");
        return patient;
    }
    //helper method to display patient details
    private void displayPatient(Patient patient) {
        System.out.println("\n--- Patient Details ---");
        System.out.println("Patient ID: " + patient.getPatientId());
        System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Phone Number: " + patient.getPhoneNumber());
    }
}
