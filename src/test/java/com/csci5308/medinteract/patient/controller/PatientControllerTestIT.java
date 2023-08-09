package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTestIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchAll() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/patient/fetchAll",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }


    @Test
    void login() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientEmail", "okabe@gmail.com");
        obj.put("patientPassword", "okabe");
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/patient/login",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }

    @Test
    void loginWithUnknownUser() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientEmail", "sdsf@gmail.com");
        obj.put("patientPassword", "okabe");
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/patient/login",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);

    }

//    @Test
//    void getPatients() {
//    }

    @Test
    void getPatientById() throws Exception {

        int patientID = 10;
        mockMvc.perform(get("http://localhost:6969/patient/profile/"+patientID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void getPatientByIdWithUnknownUser() throws Exception {
        int patientId = 1111;
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/patient/profile/"+patientId,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }

    @Test
    void registerNewPatient() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientEmail","patientName@gmail.com");
        obj.put("patientPassword","password");
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/patient/register",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }

    @Test
    void registerExistingPatient() throws Exception {



        JSONObject obj = new JSONObject();
        obj.put("patientEmail","maulvifaizal@gmail.com");
        obj.put("patientPassword","1234");
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/patient/register",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);

    }

    @Test
    void updateDoctorById() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("patientEmail","patientName@gmail.com");
        obj.put("patientPassword","password");
        obj.put("id", "1715");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/patient/updateProfile";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("profileImage", file.getName(), "image/jpeg", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void updateDoctorByIdWithoutImage() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("patientEmail","patientName@gmail.com");
        obj.put("patientPassword","password");
        obj.put("id", "1715");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/patient/updateProfile";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, null, mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void updateDoctorByInvalidId() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        JSONObject obj = new JSONObject();
        obj.put("patientEmail","patientName@gmail.com");
        obj.put("patientPassword","password");
        obj.put("id", "-1");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/patient/updateProfile";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("profileImage", file.getName(), "image/jpeg", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }



    @Test
    void fetchPatientsWithAppointment() throws Exception {

        int doctorID = 11;
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/patient/patientAppointment/"+doctorID,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }
}