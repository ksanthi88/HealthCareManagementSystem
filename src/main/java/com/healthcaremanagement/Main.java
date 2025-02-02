package com.healthcaremanagement;


import com.healthcaremanagement.runner.ManagerClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("patient.cfg.xml").buildSessionFactory();
        ManagerClass managerClass = new ManagerClass(sessionFactory);
        managerClass.healthRunner();
    }
}