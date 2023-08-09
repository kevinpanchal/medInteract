package com.csci5308.medinteract.feedback.controller;

import com.csci5308.medinteract.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveFeedback() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);
        obj.put("doctorId", 37);
        String json = obj.toString();
        MvcResult mvcResult =  TestUtil.getResultFromPostAPI("/feedback/saveFeedback",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchFeedbackByDoctorIdAndPatient() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);
        obj.put("doctorId", 13);
        String json = obj.toString();
        MvcResult mvcResult =  TestUtil.getResultFromPostAPI("/feedback/fetchFeedback_by_doctorId_and_patient",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }

    @Test
    void findAvgRatingOfDoctor() throws Exception {
        MvcResult mvcResult =  TestUtil.getResultFromGetAPI("/feedback/fetchAvgFeedback",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}