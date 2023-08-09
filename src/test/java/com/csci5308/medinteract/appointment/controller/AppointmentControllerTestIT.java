package com.csci5308.medinteract.appointment.controller;

import com.csci5308.medinteract.TestUtil;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void registerAppointment() throws Exception {


        JSONObject obj = new JSONObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        obj.put("id", 0);
        obj.put("patientId", 12);
        obj.put("doctorId", 37);
        obj.put("colorCode", "#f44437");
        obj.put("startTime", "2023-03-02T22:37:19.703Z");
        obj.put("endTime", "2023-03-02T22:37:19.703Z");
        obj.put("title", "appointment for eye operation");
        obj.put("description", "EYE OPERATION");
        obj.put("active", true);
        String json = obj.toString();

        MvcResult mcv = mockMvc.perform(post("http://localhost:6969/appointment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointment registered Successfully!"))
                .andExpect(jsonPath("$.isError").value("false")).andReturn();

    }

    @Test
    void registerAppointmentWithEmptyPatientOrDoctor() throws Exception {
        JSONObject obj = new JSONObject();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        obj.put("patientId", -1);
        obj.put("doctorId", -1);

        String json = obj.toString();

        mockMvc.perform(post("/appointment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.msg").value("An unknown error occurred!"))
                .andExpect(jsonPath("$.isError").value("true")).andReturn();

    }

    @Test
    void fetchAppointmentsByDoctor() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("patientId", 12);

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByPatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void fetchAppointmentsByPatient() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorId", 37);

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }


    @Test
    void updateAppointment() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorId", 37);
        obj.put("patientId", 12);
        obj.put("startTime", "2023-04-02T04:57:35.886Z");
        obj.put("endTime", "2023-04-02T04:57:35.886Z");
        obj.put("title", "string");
        obj.put("active", true);


        String json = obj.toString();

        MvcResult mvcResult =  mockMvc.perform(post("/appointment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)).andReturn();
        Boolean isError = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.isError");


        assertFalse(isError);

    }

    @Test
    void updateAppointmentWithInvalidID() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("doctorId", -1);
        obj.put("patientId", -1);
        obj.put("startTime", "2023-04-02T04:57:35.886Z");
        obj.put("endTime", "2023-04-02T04:57:35.886Z");
        obj.put("title", "string");
        obj.put("active", true);
        String json = obj.toString();
        String apiURL = "/appointment/update";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);

    }

    @Test
    void deleteAppointment() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorId", 37);
        obj.put("patientId", 12);
        obj.put("startTime", "2023-04-02T04:57:35.886Z");
        obj.put("endTime", "2023-04-02T04:57:35.886Z");
        obj.put("title", "string");
        obj.put("active", false);
        String json = obj.toString();
        String apiURL = "/appointment/delete";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void deleteAppointmentWithInvalidID() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorId", -1);
        obj.put("patientId", -1);
        obj.put("startTime", "2023-04-02T04:57:35.886Z");
        obj.put("endTime", "2023-04-02T04:57:35.886Z");
        obj.put("title", "string");
        obj.put("active", true);
        String json = obj.toString();
        String apiURL = "/appointment/delete";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }

    @Test
    void fetchDoctorNamesByAppointments() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);

        String json = obj.toString();
        String apiURL = "/appointment/fetchDoctorNamesByAppointments";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchAppointmentsByPatientAfterDate() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);
        obj.put("startTime","2023-04-02T05:44:54.389Z");
        obj.put("endTime","2023-04-02T05:44:54.389Z");

        String json = obj.toString();
        String apiURL = "/appointment/fetchAppointmentsByPatientAfterDate";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}