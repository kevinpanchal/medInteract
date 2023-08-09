package com.csci5308.medinteract.admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AdminModelTest {
    @Test
    void testConstructorWithEmailPassword() {

        // Arrange
        AdminModel actualAdminModel = new AdminModel();

        // Act
        actualAdminModel.setActive(true);
        actualAdminModel.setAdminEmail("memo@dal.ca");
        actualAdminModel.setAdminPassword("12345");

        // Assert
        assertTrue(actualAdminModel.isActive());
    }

    @Test
    void testConstructorWithUsernameEmailPassword() {

        // Arrange
        AdminModel actualAdminModel = new AdminModel("MeMoRam", "12345");

        // Act
        actualAdminModel.setActive(true);
        actualAdminModel.setAdminEmail("memo@dal.ca");
        actualAdminModel.setAdminPassword("12345");

        // Assert
        assertTrue(actualAdminModel.isActive());
    }
}

