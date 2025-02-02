//package com.healthcaremanagement.runner;
//
//import com.healthcaremanagement.model.Appointment;
//import com.healthcaremanagement.model.Doctor;
//import com.healthcaremanagement.model.Office;
//import com.healthcaremanagement.model.Patient;
//import com.healthcaremanagement.repository.AppointmentRepositoryImpl;
//import com.healthcaremanagement.repository.DoctorRepositoryImpl;
//
//import com.healthcaremanagement.repository.OfficeRepositoryImpl;
//import com.healthcaremanagement.repository.PatientRepositoryImpl;
//import com.healthcaremanagement.service.AppointmentService;
//import com.healthcaremanagement.service.DoctorService;
//import com.healthcaremanagement.service.OfficeService;
//import com.healthcaremanagement.service.PatientService;
//import org.hibernate.SessionFactory;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.Scanner;
//
//public class HealthRunner {
//    private final SessionFactory sessionFactory;
//    private final PatientService patientService;
//    private final AppointmentService appointmentService;
//    private final DoctorService doctorService;
//private final OfficeService officeService;
//    private final Scanner scanner;
//
//
//    public HealthRunner(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//        this.patientService = new PatientService(new PatientRepositoryImpl(sessionFactory));
//        this.appointmentService = new AppointmentService(new AppointmentRepositoryImpl(sessionFactory));
//        this.doctorService = new DoctorService(new DoctorRepositoryImpl(sessionFactory));
//        this.officeService=new OfficeService(new OfficeRepositoryImpl(sessionFactory));
//
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void healthRunner() {
//        while (true) {
//            System.out.println("\n--- HealthCare Management System ---");
//            System.out.println("1. Manage Doctors");
//            System.out.println("2. Manage Patients");
//            System.out.println("3. Manage Appointments");
//            System.out.println("4. Manage Offices");
//            System.out.println("5. Exit");
//            System.out.print("Please enter your choice: ");
//
//            int choice = getValidChoice();
//            switch (choice) {
//                case 1 -> manageDoctors();
//                case 2 -> managePatients();
//                case 3 -> manageAppointments();
//                case 4 -> manageOffices();
//                case 5 -> {
//                    System.out.println("Thank you for using HealthCare Management System");
//                    scanner.close();
//                    sessionFactory.close();
//                    return;
//                }
//                default -> System.out.println("Invalid choice! Please try again.");
//            }
//        }
//    }
////helper method to give valid input
//    private int getValidChoice() {
//        while (true) {
//            if (scanner.hasNextInt()) {
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // Consume newline
//                return choice;
//            } else {
//                System.out.println("Invalid input! Please enter a number.");
//                scanner.next(); // Discard invalid input
//            }
//        }
//    }
//
//    private void manageDoctors() {
//        while (true) {
//            System.out.println("\n--- Doctor Management ---");
//            System.out.println("1. Create Doctor");
//            System.out.println("2. Read Doctor");
//            System.out.println("3. Update Doctor");
//            System.out.println("4. Delete Doctor");
//            System.out.println("5. Return to Main Menu");
//            System.out.print("Enter your choice: ");
//
//            int choice = getValidChoice();
//            switch (choice) {
//                case 1 -> createDoctor();
//                case 2 -> {
//                    Doctor doctor = getDoctorById();
//                    if (doctor != null) displayDoctor(doctor);
//                }
//                case 3 -> {
//                    Doctor doctor = getDoctorById();
//                    if (doctor != null) updateDoctor(doctor);
//                }
//                case 4 -> {
//                    Doctor doctor = getDoctorById();
//                    if (doctor != null) {
//                        doctorService.deleteDoctorById(doctor.getDoctorId());
//                        System.out.println("Doctor deleted successfully.");
//                    }
//                }
//                case 5 -> {
//                    return;
//                }
//                default -> System.out.println("Invalid choice.");
//            }
//        }
//    }
//
//    private void createDoctor() {
//        Doctor newDoctor = new Doctor();
//        System.out.print("Enter First Name: ");
//        newDoctor.setFirstName(scanner.nextLine());
//        System.out.print("Enter Last Name: ");
//        newDoctor.setLastName(scanner.nextLine());
//        System.out.print("Enter Specialty: ");
//        newDoctor.setSpecialty(scanner.nextLine());
//        System.out.print("Enter Email: ");
//        newDoctor.setEmail(scanner.nextLine());
//        doctorService.createDoctor(newDoctor);
//        System.out.println("Doctor created successfully.");
//    }
//    private void updateDoctor(Doctor doctor) {
//        System.out.println("\n--- Update Doctor ---");
//        System.out.print("Enter new First Name (Press Enter to keep current: " + doctor.getFirstName() + "): ");
//        String firstName = scanner.nextLine();
//        if (!firstName.isEmpty()) doctor.setFirstName(firstName);
//
//        System.out.print("Enter new Last Name (Press Enter to keep current: " + doctor.getLastName() + "): ");
//        String lastName = scanner.nextLine();
//        if (!lastName.isEmpty()) doctor.setLastName(lastName);
//
//        System.out.print("Enter new Specialty (Press Enter to keep current: " + doctor.getSpecialty() + "): ");
//        String specialty = scanner.nextLine();
//        if (!specialty.isEmpty()) doctor.setSpecialty(specialty);
//
//        System.out.print("Enter new Email (Press Enter to keep current: " + doctor.getEmail() + "): ");
//        String email = scanner.nextLine();
//        if (!email.isEmpty()) doctor.setEmail(email);
//
//        doctorService.updateDoctor(doctor);
//        System.out.println("Doctor updated successfully.");
//    }
//
//
//    private void managePatients() {
//        while (true) {
//            System.out.println("\n--- Patient Management ---");
//            System.out.println("1. Create Patient");
//            System.out.println("2. Read Patient");
//            System.out.println("3. Update Patient");
//            System.out.println("4. Delete Patient");
//            System.out.println("5. Return to Main Menu");
//            System.out.print("Enter your choice: ");
//
//            int choice = getValidChoice();
//            switch (choice) {
//                case 1 -> createPatient();
//                case 2 -> {
//                    Patient patient = getPatientById();
//                    if (patient != null) displayPatient(patient);
//                }
//                case 3 -> {
//                    Patient patient = getPatientById();
//                    if (patient != null) updatePatient(patient);
//                }
//                case 4 -> {
//                    Patient patient = getPatientById();
//                    if (patient != null) {
//                        patientService.deletePatient(patient.getPatientId());
//                        System.out.println("Patient deleted successfully.");
//                    }
//                }
//                case 5 -> {
//                    return;
//                }
//                default -> System.out.println("Invalid choice.");
//            }
//        }
//    }
//
//    private void createPatient() {
//        Patient newPatient = new Patient();
//        System.out.print("Enter First Name: ");
//        newPatient.setFirstName(scanner.nextLine());
//        System.out.print("Enter Last Name: ");
//        newPatient.setLastName(scanner.nextLine());
//        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
//        newPatient.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
//        System.out.print("Enter Email: ");
//        newPatient.setEmail(scanner.nextLine());
//        System.out.print("Enter Phone Number: ");
//        newPatient.setPhoneNumber(scanner.nextLine());
//        patientService.createPatient(newPatient);
//        System.out.println("Patient created successfully.");
//    }
//    private void updatePatient(Patient patient) {
//        System.out.println("\n--- Update Patient ---");
//
//        System.out.print("Enter new First Name (Press Enter to keep current: " + patient.getFirstName() + "): ");
//        String firstName = scanner.nextLine();
//        if (!firstName.isEmpty()) patient.setFirstName(firstName);
//
//        System.out.print("Enter new Last Name (Press Enter to keep current: " + patient.getLastName() + "): ");
//        String lastName = scanner.nextLine();
//        if (!lastName.isEmpty()) patient.setLastName(lastName);
//
//        System.out.print("Enter new Date of Birth (yyyy-MM-dd, Press Enter to keep current: " + patient.getDateOfBirth() + "): ");
//        String dob = scanner.nextLine();
//        if (!dob.isEmpty()) {
//            try {
//                patient.setDateOfBirth(LocalDate.parse(dob));
//            } catch (DateTimeParseException e) {
//                System.out.println("Invalid date format! Keeping previous value.");
//            }
//        }
//
//        System.out.print("Enter new Email (Press Enter to keep current: " + patient.getEmail() + "): ");
//        String email = scanner.nextLine();
//        if (!email.isEmpty()) patient.setEmail(email);
//
//        System.out.print("Enter new Phone Number (Press Enter to keep current: " + patient.getPhoneNumber() + "): ");
//        String phoneNumber = scanner.nextLine();
//        if (!phoneNumber.isEmpty()) patient.setPhoneNumber(phoneNumber);
//
//        patientService.updatePatient(patient);
//        System.out.println("Patient updated successfully.");
//    }
//    private void manageAppointments() {
//        while (true) {
//            System.out.println("\n--- Appointment Management ---");
//            System.out.println("1. Create Appointment");
//            System.out.println("2. Read Appointment");
//            System.out.println("3. Update Appointment");
//            System.out.println("4. Delete Appointment");
//            System.out.println("5. Return to Main Menu");
//            System.out.print("Enter your choice: ");
//
//            int choice = getValidChoice();
//            switch (choice) {
//                case 1 -> createAppointment();
//                case 2 -> readAppointment();
//                case 3 -> updateAppointment();
//                case 4 -> deleteAppointment();
//                case 5 -> {
//                    return;
//                }
//                default -> System.out.println("Invalid choice.");
//            }
//        }
//    }
//
//    private void createAppointment() {
//        Appointment newAppointment = new Appointment();
//        Patient patient = getPatientById();
//        Doctor doctor = getDoctorById();
//
//        if (patient != null && doctor != null) {
//            newAppointment.setPatient(patient);
//            newAppointment.setDoctor(doctor);
//            newAppointment.setAppointmentDate(getValidDate());
//            System.out.print("Enter Appointment Notes: ");
//            newAppointment.setNotes(scanner.nextLine());
//
//            appointmentService.createAppointment(newAppointment);
//            System.out.println("Appointment created successfully.");
//        }
//    }
//    private void readAppointment() {
//        Appointment appointment = getAppointmentById();
//        if (appointment != null) {
//            displayAppointment(appointment);
//        }
//    }
//
//    private void updateAppointment() {
//        Appointment appointment = getAppointmentById();
//        if (appointment != null) {
//            System.out.println("\n--- Update Appointment ---");
//
//            System.out.print("Enter new Patient ID (Press Enter to keep current: " + appointment.getPatient().getPatientId() + "): ");
//            String patientId = scanner.nextLine();
//            if (!patientId.isEmpty()) {
//                Patient updatedPatient = patientService.getPatientById(Integer.parseInt(patientId));
//                if (updatedPatient != null) {
//                    appointment.setPatient(updatedPatient);
//                } else {
//                    System.out.println("Patient not found, keeping existing patient.");
//                }
//            }
//
//            System.out.print("Enter new Doctor ID (Press Enter to keep current: " + appointment.getDoctor().getDoctorId() + "): ");
//            String doctorId = scanner.nextLine();
//            if (!doctorId.isEmpty()) {
//                Doctor updatedDoctor = doctorService.getDoctorById(Integer.parseInt(doctorId));
//                if (updatedDoctor != null) {
//                    appointment.setDoctor(updatedDoctor);
//                } else {
//                    System.out.println("Doctor not found, keeping existing doctor.");
//                }
//            }
//
//            System.out.print("Enter new Appointment Date (yyyy-MM-dd HH:mm, Press Enter to keep current: " + appointment.getAppointmentDate() + "): ");
//            String appointmentDate = scanner.nextLine();
//            if (!appointmentDate.isEmpty()) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                appointment.setAppointmentDate(LocalDateTime.parse(appointmentDate, formatter));
//            }
//
//            System.out.print("Enter new Notes (Press Enter to keep current: " + appointment.getNotes() + "): ");
//            String notes = scanner.nextLine();
//            if (!notes.isEmpty()) appointment.setNotes(notes);
//
//            appointmentService.updateAppointment(appointment);
//            System.out.println("Appointment updated successfully.");
//        }
//    }
//
//    private void deleteAppointment() {
//        Appointment appointment = getAppointmentById();
//        if (appointment != null) {
//            appointmentService.deleteAppointmentById(appointment.getAppointmentId());
//            System.out.println("Appointment deleted successfully.");
//        }
//    }
//
//    private Appointment getAppointmentById() {
//        System.out.print("Enter Appointment ID: ");
//        int id = getValidChoice();
//        Appointment appointment = appointmentService.getAppointmentById(id);
//        if (appointment == null) {
//            System.out.println("Appointment not found.");
//        }
//        return appointment;
//    }
//
//    private LocalDateTime getValidDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        while (true) {
//            System.out.print("Enter date (yyyy-MM-dd HH:mm): ");
//            try {
//                return LocalDateTime.parse(scanner.nextLine(), formatter);
//            } catch (DateTimeParseException e) {
//                System.out.println("Invalid date format! Please enter again.");
//            }
//        }
//    }
////Helper method to getting patient id
//    private Patient getPatientById() {
//        System.out.print("Enter Patient ID: ");
//        int id = getValidChoice();
//        Patient patient = patientService.getPatientById(id);
//        if (patient == null) System.out.println("Patient not found.");
//        return patient;
//    }
//    //helper method to display patient details
//    private void displayPatient(Patient patient) {
//        System.out.println("\n--- Patient Details ---");
//        System.out.println("Patient ID: " + patient.getPatientId());
//        System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
//        System.out.println("Date of Birth: " + patient.getDateOfBirth());
//        System.out.println("Email: " + patient.getEmail());
//        System.out.println("Phone Number: " + patient.getPhoneNumber());
//    }
//
//    private Doctor getDoctorById() {
//        System.out.print("Enter Doctor ID: ");
//        int id = getValidChoice();
//        Doctor doctor = doctorService.getDoctorById(id);
//        if (doctor == null) System.out.println("Doctor not found.");
//        return doctor;
//    }
//    private void displayDoctor(Doctor doctor) {
//        System.out.println("\n--- Doctor Details ---");
//        System.out.println("Doctor ID: " + doctor.getDoctorId());
//        System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
//        System.out.println("Specialty: " + doctor.getSpecialty());
//        System.out.println("Email: " + doctor.getEmail());
//    }
//    private void displayAppointment(Appointment appointment) {
//        System.out.println("\n--- Appointment Details ---");
//        System.out.println("Appointment ID: " + appointment.getAppointmentId());
//        System.out.println("Patient ID: " + appointment.getPatient().getPatientId());
//        System.out.println("Doctor ID: " + appointment.getDoctor().getDoctorId());
//        System.out.println("Appointment Date: " + appointment.getAppointmentDate());
//        System.out.println("Notes: " + appointment.getNotes());
//    }
//    private void manageOffices(){
//        while (true) {
//            System.out.println("\n--- Office Management ---");
//            System.out.println("1. Create Office");
//            System.out.println("2. Read Office");
//            System.out.println("3. Update Office");
//            System.out.println("4. Delete Office");
//            System.out.println("5. List All Offices");
//            System.out.println("6. Return to Main Menu");
//            System.out.print("Enter your choice: ");
//
//            int choice = getValidChoice();
//            switch (choice) {
//                case 1 -> createNewOffice();
//                case 2 -> readOffice();
//                case 3 -> updateOffice();
//                case 4 -> deleteOfficeById();
//                case 5 -> listAllOffice();
//                case 6 -> {
//                    return;
//                }
//                default -> System.out.println("Invalid choice.");
//            }
//        }
//    }
////Helping method for Manage office
//private void createNewOffice() {
//    Office  newOffice = new Office();
//    System.out.print("Enter Office Location: ");
//    newOffice.setLocation(scanner.nextLine());
//    System.out.print("Enter Office Phone: ");
//    newOffice.setPhone(scanner.nextLine());
//    Doctor doctor = getDoctorById();
//    if(doctor != null) {
//        newOffice.setDoctor(doctor);
//    }else{
//        System.out.println("Doctor not found.");
//    }
//    officeService.createOffice(newOffice);
//    System.out.println("Office created Successfully.");
//}
//private void readOffice() {
//        Office office= getOfficeById();
//        if(office != null) {
//            displayOffice(office);
//        }
//}
//    private Office getOfficeById() {
//        System.out.print("Enter  Office ID: ");
//        int id = getValidChoice();
//        Office office = officeService.getOfficeById(id);
//        if (office == null) System.out.println("Patient not found.");
//        return office;
//    }
//    private void displayOffice(Office office) {
//        System.out.println("\n--- Office Details ---");
//        System.out.println("Office ID: " + office.getOfficeId());
//        System.out.println("Office Location: " + office.getLocation());
//        System.out.println("Office Phone: " + office.getPhone());
//        System.out.println("Doctor ID: " + office.getDoctor().getDoctorId());
//
//    }
//    private void updateOffice() {
//        Office office = getOfficeById();
//        if (office != null) {
//            System.out.print("Update Office ID: ");
//            office.setOfficeId(getValidChoice());
//            office.setLocation(scanner.nextLine());
//            office.setPhone(scanner.nextLine());
//            office.setDoctor(getDoctorById());
//            officeService.updateOffice(office);
//            System.out.println("Office updated Successfully.");
//        }
//    }
//    private void deleteOfficeById() {
//        Office office = getOfficeById();
//        if (office != null) {
//            officeService.deleteOfficeById(office.getOfficeId());
//            System.out.println("Office deleted Successfully.");
//        }
//    }
//    private void listAllOffice() {
//        List<Office> offices = officeService.getAllOffices();
//        offices.forEach(System.out::println);
//
//    }
//}