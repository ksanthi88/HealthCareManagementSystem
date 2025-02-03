package com.healthcaremanagement.runner;

import com.healthcaremanagement.model.Doctor;
import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.service.AppointmentService;
import com.healthcaremanagement.service.DoctorService;
import com.healthcaremanagement.service.OfficeService;

import java.util.List;
import java.util.Scanner;

public class OfficeManager {
    private final OfficeService officeService;
    private final DoctorService doctorService;
    private final Scanner scanner;

    public OfficeManager(OfficeService officeService, Scanner scanner,DoctorService doctorService) {
        this.officeService = officeService;
        this.doctorService = doctorService;
        this.scanner = scanner;
    }

    public void manageOffices() {
        while (true) {
            System.out.println("\n--- Office Management ---");
            System.out.println("1. Create Office");
            System.out.println("2. Read Office");
            System.out.println("3. Update Office");
            System.out.println("4. Delete Office");
            System.out.println("5. List All Offices");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidChoice();
            switch (choice) {
                case 1 -> createNewOffice();
                case 2 -> readOffice();
                case 3 -> updateOffice();
                case 4 -> deleteOfficeById();
                case 5 -> listAllOffice();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    //Helping method for Manage office
    private void createNewOffice() {
        Office newOffice = new Office();
        System.out.print("Enter Office Location: ");
        newOffice.setLocation(scanner.nextLine());
        System.out.print("Enter Office Phone: ");
        newOffice.setPhone(scanner.nextLine());
        Doctor doctor = getDoctorById();
        if (doctor != null) {
            newOffice.setDoctor(doctor);
        } else {
            System.out.println("Doctor not found.");
        }
        officeService.createOffice(newOffice);
        System.out.println("Office created Successfully.");
    }

    private void readOffice() {
        Office office = getOfficeById();
        if (office != null) {
            displayOffice(office);
        }
    }

    private Office getOfficeById() {
        System.out.print("Enter  Office ID: ");
        int id = getValidChoice();
        Office office = officeService.getOfficeById(id);
        if (office == null) System.out.println("Patient not found.");
        return office;
    }

    private void displayOffice(Office office) {
        System.out.println("\n--- Office Details ---");
        System.out.println("Office ID: " + office.getOfficeId());
        System.out.println("Office Location: " + office.getLocation());
        System.out.println("Office Phone: " + office.getPhone());
        System.out.println("Doctor ID: " + office.getDoctor().getDoctorId());

    }

    private void updateOffice() {
        Office office = getOfficeById();
        if (office != null) {
            System.out.print("Update Office ID: ");
            office.setOfficeId(getValidChoice());
            office.setLocation(scanner.nextLine());
            office.setPhone(scanner.nextLine());
            office.setDoctor(getDoctorById());
            officeService.updateOffice(office);
            System.out.println("Office updated Successfully.");
        }
    }

    private void deleteOfficeById() {
        Office office = getOfficeById();
        if (office != null) {
            officeService.deleteOfficeById(office.getOfficeId());
            System.out.println("Office deleted Successfully.");
        }
    }

    private void listAllOffice() {
        List<Office> offices = officeService.getAllOffices();
        offices.forEach(System.out::println);

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

    private Doctor getDoctorById() {
        System.out.print("Enter Doctor ID: ");
        int id = getValidChoice();
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) System.out.println("Doctor not found.");
        return doctor;
    }

}

