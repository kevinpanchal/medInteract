package com.csci5308.medinteract.admin.service;

import com.csci5308.medinteract.admin.model.AdminModel;
import com.csci5308.medinteract.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AdminService.class)
class AdminServiceTest {
    @MockBean
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Test
    void testIsAdminValid() {

        // Arrange
        AdminModel adminModel = new AdminModel();

        // Act
        adminModel.setActive(true);
        adminModel.setAdminEmail("memo@dal.ca");
        adminModel.setAdminPassword("12345");
        when(adminRepository.getReferenceById(Mockito.<String>any())).thenReturn(adminModel);

        // Assert
        assertTrue(adminService.isAdminValid("memo@dal.ca", "12345"));
        verify(adminRepository).getReferenceById(Mockito.<String>any());
    }
}

