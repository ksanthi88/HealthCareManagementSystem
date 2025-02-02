package com.healthcaremanagement.service;

import com.healthcaremanagement.model.Office;
import com.healthcaremanagement.repository.OfficeRepositoryImpl;

import java.util.List;

public class OfficeService {
    private final OfficeRepositoryImpl officeRepositoryImpl;
    public OfficeService(OfficeRepositoryImpl officeRepositoryImpl) {
        this.officeRepositoryImpl = officeRepositoryImpl;
    }
    public void createOffice(Office office) {
        officeRepositoryImpl.createOffice(office);
    }
    public List<Office> getAllOffices() {
        return officeRepositoryImpl.getAllOffices();
    }
    public Office getOfficeById(int id) {
         return officeRepositoryImpl.getOfficeById(id);
    }
    public void deleteOfficeById(int id) {
        officeRepositoryImpl.deleteOfficeById(id);
    }
    public void updateOffice(Office office) {
        officeRepositoryImpl.updateOffice(office);
    }
}
