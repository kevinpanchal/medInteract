package com.csci5308.medinteract.notification.controller;

import com.csci5308.medinteract.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTestIT {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void fetchAll() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("userType", "string");

        String json = obj.toString();
        MvcResult mvcResult =TestUtil.getResultFromPostAPI("/notification/fetchAll",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}