package com.healthcaremanagement.runner;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.service.DoctorService;

import java.util.Scanner;


public class DoctorManager {
    private final DoctorService doctorService;
    private final Scanner scanner;

    public DoctorManager(DoctorService doctorService, Scanner scanner) {
        this.doctorService = doctorService;
        this.scanner = scanner;
    }
    public void manageDoctors() {
        while (true) {
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Create Doctor");
            System.out.println("2. Read Doctor");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidChoice();
            switch (choice) {
                case 1 -> createDoctor();
                case 2 -> {
                    Doctor doctor = getDoctorById();
                    if (doctor != null) displayDoctor(doctor);
                }
                case 3 -> {
                    Doctor doctor = getDoctorById();
                    if (doctor != null) updateDoctor(doctor);
                }
                case 4 -> {
                    Doctor doctor = getDoctorById();
                    if (doctor != null) {
                        doctorService.deleteDoctorById(doctor.getDoctorId());
                        System.out.println("Doctor deleted successfully.");
                    }
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
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
    private void createDoctor() {
        Doctor newDoctor = new Doctor();
        System.out.print("Enter First Name: ");
        newDoctor.setFirstName(scanner.nextLine());
        System.out.print("Enter Last Name: ");
        newDoctor.setLastName(scanner.nextLine());
        System.out.print("Enter Specialty: ");
        newDoctor.setSpecialty(scanner.nextLine());
        System.out.print("Enter Email: ");
        newDoctor.setEmail(scanner.nextLine());
        doctorService.createDoctor(newDoctor);
        System.out.println("Doctor created successfully.");
    }
    private void updateDoctor(Doctor doctor) {
        System.out.println("\n--- Update Doctor ---");
        System.out.print("Enter new First Name (Press Enter to keep current: " + doctor.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) doctor.setFirstName(firstName);

        System.out.print("Enter new Last Name (Press Enter to keep current: " + doctor.getLastName() + "): ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) doctor.setLastName(lastName);

        System.out.print("Enter new Specialty (Press Enter to keep current: " + doctor.getSpecialty() + "): ");
        String specialty = scanner.nextLine();
        if (!specialty.isEmpty()) doctor.setSpecialty(specialty);

        System.out.print("Enter new Email (Press Enter to keep current: " + doctor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) doctor.setEmail(email);

        doctorService.updateDoctor(doctor);
        System.out.println("Doctor updated successfully.");
    }
    private Doctor getDoctorById() {
        System.out.print("Enter Doctor ID: ");
        int id = getValidChoice();
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) System.out.println("Doctor not found.");
        return doctor;
    }
    private void displayDoctor(Doctor doctor) {
        System.out.println("\n--- Doctor Details ---");
        System.out.println("Doctor ID: " + doctor.getDoctorId());
        System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
        System.out.println("Specialty: " + doctor.getSpecialty());
        System.out.println("Email: " + doctor.getEmail());
    }
}
