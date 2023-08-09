package com.csci5308.medinteract.province.model;

import com.csci5308.medinteract.city.model.CityModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProvinceModelTest {

    @Test
    void testConstructor() {

        // Arrange
        ProvinceModel actualProvinceModel = new ProvinceModel();
        ArrayList<CityModel> cities = new ArrayList<>();

        // Act
        actualProvinceModel.setAbbrv("NS");
        actualProvinceModel.setCities(cities);
        actualProvinceModel.setId(1L);
        actualProvinceModel.setName("Nova Scotia");

        // Assert
        assertEquals("NS", actualProvinceModel.getAbbrv());
    }

    @Test
    void testGetIsActive() {
        ProvinceModel provinceModel = new ProvinceModel();
        provinceModel.setAbbrv("NS");
        provinceModel.setCities(new ArrayList<>());
        provinceModel.setId(1L);
        provinceModel.setIsActive(true);
        provinceModel.setName("Nova Scotia");
        assertTrue(provinceModel.getIsActive());
    }

    @Test
    void testSetIsActive() {
        ProvinceModel provinceModel = new ProvinceModel();
        provinceModel.setIsActive(true);
        assertTrue(provinceModel.getIsActive());
    }
}

