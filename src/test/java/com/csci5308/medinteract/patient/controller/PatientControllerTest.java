package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.patient.service.PatientServiceImpl;
import com.csci5308.medinteract.JWT.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.MediaType;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private PatientRepository patientRepository;

    @MockBean
    private JWT jwt;

    //@MockBean
    //private PatientService patientService;

    @MockBean
    private PatientServiceImpl patientService;
    private PatientModel mockPatientModel = new PatientModel();

    private String patientJSON = "{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"patientPass\" }";

    PatientControllerTest() {
        mockPatientModel.setId(101l);
        mockPatientModel.setPatientEmail("paitent@gamil.com");
        mockPatientModel.setPatientPassword("patientPass");
    }
    @Test
    void loginTest() throws Exception {

        Mockito.when(patientService.isPatientValid(Mockito.anyString(),
                Mockito.anyString())).thenReturn(true);

        Mockito.when(patientService.getPatientByEmail(Mockito.anyString()
        )).thenReturn(mockPatientModel);

        mockMvc.perform(post("http://localhost:6969/patient/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void loginFailedTest() throws Exception {

        Mockito.when(patientService.isPatientValid(Mockito.anyString(),
                Mockito.anyString())).thenReturn(false);

        Mockito.when(patientService.getPatientByEmail(Mockito.anyString()
        )).thenReturn(mockPatientModel);

        mockMvc.perform(post("http://localhost:6969/patient/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Failed to login for the given credentials"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void fetchAllTest() throws Exception {

        List<PatientModel> mockListPatients = new ArrayList<PatientModel>();
        mockListPatients.add(mockPatientModel);
        Mockito.when(patientService.fetchAll()).thenReturn(mockListPatients);

        mockMvc.perform(get("http://localhost:6969/patient/fetchAll")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All Patients Fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));


        List<PatientModel> allPatient = patientService.fetchAll();

        assertEquals(allPatient, mockListPatients);


    }

    @Test
    void registerFailTest() throws Exception {

        Map<String, Object> res = new HashMap<>();

        res.put("result", true);
        res.put("id", mockPatientModel.getId());
        res.put("data", mockPatientModel);

        Mockito.when(patientService.checkIfEmailExists(Mockito.anyString())).thenReturn(res);

        mockMvc.perform(post("http://localhost:6969/patient/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient with email already exists!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void registerTest() throws Exception {

        Map<String, Object> res = new HashMap<>();

        res.put("result", false);
        res.put("id", mockPatientModel.getId());
        res.put("data", mockPatientModel);

        Mockito.when(patientService.checkIfEmailExists(Mockito.anyString())).thenReturn(res);

        mockMvc.perform(post("http://localhost:6969/patient/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("User registered Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void getPatientByIdTest() throws Exception {

        mockPatientModel.setBlocked(false);
        mockPatientModel.setActive(true);

        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockPatientModel));

        mockMvc.perform(get("http://localhost:6969/patient/profile/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void getPatientByIdFailedTest() throws Exception {

        mockPatientModel.setBlocked(true);
        mockPatientModel.setActive(false);

        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockPatientModel));

        mockMvc.perform(get("http://localhost:6969/patient/profile/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Unable to find patient with the given id!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }


   @Test
   void fetchPatientsWithAppointmentTest() throws Exception {
       List<PatientModel> mockListpatients = new ArrayList<>();
       Mockito.when(patientService.fetchPatientsWithAppointment(Mockito.any())).thenReturn(Optional.of(mockListpatients));

       mockMvc.perform(get("http://localhost:6969/patient/patientAppointment/{doctorId}",100)
                       .contentType(MediaType.APPLICATION_JSON)
                       )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.msg").value("Patients fetched Successfully!"))
               .andExpect(jsonPath("$.isError").value("false"));

   }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
