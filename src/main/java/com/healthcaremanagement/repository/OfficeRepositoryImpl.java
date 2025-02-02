package com.healthcaremanagement.repository;

import com.healthcaremanagement.model.Office;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class OfficeRepositoryImpl {
    private final SessionFactory sessionFactory;
    public OfficeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
public void createOffice(Office office) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(office);
        session.getTransaction().commit();
}
public Office getOfficeById(int id) {
        Session session = sessionFactory.openSession();
        Office office = session.get(Office.class, id);
        return office;

}
public void updateOffice(Office office) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(office);
        session.getTransaction().commit();
}
public void deleteOfficeById(int id) {
        Session session = sessionFactory.openSession();

        Office office = session.get(Office.class, id);
        session.remove(office);

}
public List<Office> getAllOffices() {
        Session session = sessionFactory.openSession();
       List<Office> offices = session.createQuery("from Office",Office.class).list();
       return offices;
}
}
