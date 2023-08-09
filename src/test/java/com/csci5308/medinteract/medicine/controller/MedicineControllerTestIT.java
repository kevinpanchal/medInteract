package com.csci5308.medinteract.medicine.controller;

import com.csci5308.medinteract.TestUtil;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicineControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void addMedicines() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("medicineName", "medicineName");
        String json = obj.toString();

        String apiURL = "/medicine/addMedicines";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchAll() throws Exception {

       mockMvc.perform(get("/medicine/fetchAll")).andExpect(status().isOk());
    }

    @Test
    void getPrescriptionById() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/medicine/fetch/1",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Medicines details fetched Successfully!", msg);
    }
}
