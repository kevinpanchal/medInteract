package com.csci5308.medinteract.medicine.controller;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.medicine.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MedicineController.class)
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MedicineRepository medicineRepository;

    @MockBean
    private MedicineService medicineService;


    @Test
    public void testFetchAll() throws Exception {
        MedicineModel medicine1 = new MedicineModel(1L, "Medicine 1", 1, true, false, false, "Medicine 1");
        MedicineModel medicine2 = new MedicineModel(2L, "Medicine 2", 2, false, true, false, "Medicine 2");
        List<MedicineModel> medicines = Arrays.asList(medicine1, medicine2);

        Mockito.when(medicineService.findAllMedicine()).thenReturn(medicines);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicine/fetchAll"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetMedicineById() throws Exception {

        MedicineModel medicine = new MedicineModel(1L, "Medicine 1", 1, true, false, false, "Medicine 1");


        Mockito.when(medicineService.findMedicineById(1l)).thenReturn(medicine);

        mockMvc.perform(get("/medicine/fetch/" + 1l)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Medicines details fetched Successfully!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isError").value("false"));
    }
}
