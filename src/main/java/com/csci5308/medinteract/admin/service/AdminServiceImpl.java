package com.csci5308.medinteract.admin.service;

import com.csci5308.medinteract.admin.model.AdminModel;
import com.csci5308.medinteract.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }



    @Override
    public boolean isAdminValid(String adminEmail, String adminPassword) {
        AdminModel admin = adminRepository.getReferenceById(adminEmail);
        return admin.getAdminPassword().equals(adminPassword);
    }
}
