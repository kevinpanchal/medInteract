package com.csci5308.medinteract.province.controller;

import com.csci5308.medinteract.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProvinceControllerTestIT {

    @Autowired
    MockMvc mockMvc;
    @Test
    void fetchAll() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/province/fetchAll",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void getProvinceId() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("name","Quebec");
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/province/province_id",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}
