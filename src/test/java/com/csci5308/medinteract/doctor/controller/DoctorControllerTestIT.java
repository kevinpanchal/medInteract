package com.csci5308.medinteract.doctor.controller;

import com.csci5308.medinteract.TestUtil;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTestIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;
    @Test
    void fetchAll() throws Exception {

            mockMvc.perform(get("http://localhost:6969/doctor/fetchAll"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("All doctors fetched Successfully!"))
                    .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void registerNewDoctor() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "ddd@gmail.com");
        obj.put("doctorPassword","abc");

        String json = obj.toString();
        String apiURL = "/doctor/register";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }
    @Test
    void registerExistingDoctor() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "maulvifaizal@gmail.com");
        obj.put("doctorPassword","1234");

        String json = obj.toString();
        String apiURL = "/doctor/register";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);

    }

    @Test
    void login() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorEmail", "maulvifaizal@gmail.com");
        obj.put("doctorPassword", "1234");

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/doctor/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void loginWithUnknownUer() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "unkown@gmail.com");
        obj.put("doctorPassword","1234");

        String json = obj.toString();
        String apiURL = "/doctor/login";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);

    }

    @Test
    void getDoctorById() throws Exception {
        int doctorId = 37;
        mockMvc.perform(get("http://localhost:6969/doctor/profile/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void getDoctorUsingUnknownId() throws Exception {
        int doctorId = 377;
        mockMvc.perform(get("/doctor/profile/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Unable to find user with the given id!"))
                .andExpect(jsonPath("$.isError").value("true"));
    }

    @Test
    void updateDoctorById() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "ddd@gmail.com");
        obj.put("doctorPassword","abc");
        obj.put("id", "3884");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/doctor/updateProfile";
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
        obj.put("doctorEmail", "ddd@gmail.com");
        obj.put("doctorPassword","abc");
        obj.put("id", "3884");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/doctor/updateProfile";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, null, mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void updateDoctorByInvalidId() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "ddd@gmail.com");
        obj.put("doctorPassword","abc");
        obj.put("id", "-1");
        String json = obj.toString();
        formData.add("objectData", json);
        String apiURL = "/doctor/updateProfile";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("profileImage", file.getName(), "image/jpeg", fileInputStream);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }

    @Test
    void isPending() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isPending",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Pending Doctors Fetched!", msg);
    }

    @Test
    void isApproved() throws Exception {

        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isApproved",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Approved Doctors Fetched!", msg);
    }

    @Test
    void isBlocked() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isBlocked",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("No Blocked Doctors!", msg);
    }

    @Test
    void verifyDoctor() throws Exception {
        String doctorEmail = "tp@gmail.com";
        boolean active= true;

        boolean block = false;

      MvcResult mvcResult =   mockMvc.perform(post("/doctor/verified?doctorEmail="+doctorEmail+"&isActive="+active+"&isBlocked="+block))
                .andReturn();

      assertEquals(mvcResult.getResponse().getStatus(),200);

    }

    @Test
    void blockDoctor() throws Exception {

        String doctorEmail = "tp@gmail.com";

        boolean block = true;

        MvcResult mvcResult =   mockMvc.perform(post("/doctor/blocked?doctorEmail="+doctorEmail+"&isBlocked="+block))
                .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);

    }

    @Test
    void fetchPrescriptionByPatientId() throws Exception {
        long patientId = 12;

        MvcResult mvcResult =   mockMvc.perform(get("/doctor/fetch/"+patientId))
                .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
    }

    @Test
    void findDoctorOnDetailsWithCity() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorName", "");
        obj.put("doctorAddressProvince", null);
        obj.put("doctorAddressPostalCode", null);
        obj.put("doctorQualification", "");
        String json = obj.toString();
        String apiURL = "/doctor/get_doctor_on_details_and_city";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }
}
