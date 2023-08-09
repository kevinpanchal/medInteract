package com.csci5308.medinteract.province.service;

import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.province.repository.ProvinceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = {ProvinceService.class})
@ExtendWith(SpringExtension.class)
class ProvinceServiceTest {
    @MockBean
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceService provinceServiceImpl;

    @Test
    void testFetchAll() {

        // Arrange
        ArrayList<ProvinceModel> provinceModelList = new ArrayList<>();
        when(provinceRepository.findAll()).thenReturn(provinceModelList);

        // Act
        List<ProvinceModel> actualFetchAllResult = provinceServiceImpl.fetchAll();

        // Assert
        assertTrue(actualFetchAllResult.isEmpty());
        verify(provinceRepository).findAll();
    }

    @Test
    void testGetProvinceId2() {

        // Arrange
        ProvinceModel provinceModel = new ProvinceModel();
        provinceModel.setAbbrv("NS");
        provinceModel.setCities(new ArrayList<>());
        provinceModel.setId(1L);
        provinceModel.setIsActive(true);
        provinceModel.setName("Nova Scotia");

        // Act
        ArrayList<ProvinceModel> provinceModelList = new ArrayList<>();
        provinceModelList.add(provinceModel);
        when(provinceRepository.findByName(Mockito.<String>any())).thenReturn(provinceModelList);

        // Assert
        assertEquals(1L, provinceServiceImpl.getProvinceId(provinceModel).longValue());
        verify(provinceRepository).findByName(Mockito.<String>any());
    }
}

