package com.csci5308.medinteract.doctor.controller;

import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.repository.DoctorRepository;
import com.csci5308.medinteract.doctor.service.DoctorService;
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
@WebMvcTest(value = DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private DoctorRepository doctorRepository;

    @MockBean
    private JWT jwt;

    @MockBean
    private DoctorService doctorService;
//    private DoctorModel mockDoctorModel = new DoctorModel("doctor@gmail.com","docPass");
    private DoctorModel mockDoctorModel = new DoctorModel();

    DoctorControllerTest() {
        mockDoctorModel.setDoctorEmail("doctor@gmail.com");
        mockDoctorModel.setDoctorPassword("docPass");
    }

    private String doctorJSON = "{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPassword\": \"docPass\" }";
    @Test
    void loginTest() throws Exception {

        Mockito.when(doctorService.isDoctorValid(Mockito.anyString(),
                Mockito.anyString())).thenReturn(true);

        Mockito.when(doctorService.getDoctorByEmail(Mockito.anyString()
        )).thenReturn(mockDoctorModel);

        mockMvc.perform(post("http://localhost:6969/doctor/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void loginFailedTest() throws Exception {

        Mockito.when(doctorService.isDoctorValid(Mockito.anyString(),
                Mockito.anyString())).thenReturn(false);

        Mockito.when(doctorService.getDoctorByEmail(Mockito.anyString()
        )).thenReturn(mockDoctorModel);

        mockMvc.perform(post("http://localhost:6969/doctor/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Failed to login for the given credentials"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void fetchAllTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        mockListDoctors.add(mockDoctorModel);
        Mockito.when(doctorService.fetchAll()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/fetchAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All doctors fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));


        List<DoctorModel> allDoctor = doctorService.fetchAll();

        assertEquals(allDoctor, mockListDoctors);


    }

    @Test
    void registerFailTest() throws Exception {

        Map<String, Object> res = new HashMap<>();

        res.put("result", true);
        res.put("id", mockDoctorModel.getId());
        res.put("data", mockDoctorModel);

        Mockito.when(doctorService.checkIfEmailExists(Mockito.anyString())).thenReturn(res);

        mockMvc.perform(post("http://localhost:6969/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor with email already exists!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void registerTest() throws Exception {

        Map<String, Object> res = new HashMap<>();

        res.put("result", false);
        res.put("id", mockDoctorModel.getId());
        res.put("data", mockDoctorModel);

        Mockito.when(doctorService.checkIfEmailExists(Mockito.anyString())).thenReturn(res);

        mockMvc.perform(post("http://localhost:6969/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPassword\": \"docPass\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("User registered Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void getDoctorByIdTest() throws Exception {

        mockDoctorModel.setBlocked(false);
        mockDoctorModel.setActive(true);

        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));

        mockMvc.perform(get("http://localhost:6969/doctor/profile/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void getDoctorByIdFailedTest() throws Exception {

        mockDoctorModel.setBlocked(true);
        mockDoctorModel.setActive(false);

        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));

        mockMvc.perform(get("http://localhost:6969/doctor/profile/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Unable to find user with the given id!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void isPendingTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        mockListDoctors.add(mockDoctorModel);
        Mockito.when(doctorService.isPending()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isPending")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Pending Doctors Fetched!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void isPendingFailedTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        Mockito.when(doctorService.isPending()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isPending")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("No Pending Doctors!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void isApprovedTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        mockListDoctors.add(mockDoctorModel);
        Mockito.when(doctorService.isApproved()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isApproved")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Approved Doctors Fetched!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void isApprovedFailedTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        Mockito.when(doctorService.isApproved()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isApproved")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("No Approved Doctors!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void isBlockedTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        mockListDoctors.add(mockDoctorModel);
        Mockito.when(doctorService.isBlocked()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isBlocked")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Blocked Doctors Fetched!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void isBlockedFailedTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        Mockito.when(doctorService.isBlocked()).thenReturn(mockListDoctors);

        mockMvc.perform(get("http://localhost:6969/doctor/isBlocked")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("No Blocked Doctors!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void verifyDoctorTest() throws Exception {

        Mockito.doNothing().when(doctorService).verifyDoctor(Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyBoolean());

        mockMvc.perform(post("http://localhost:6969/doctor/verified?doctorEmail=\"doctorEmail\"&isActive=true&isBlocked=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void blockDoctorTest() throws Exception {

        Mockito.doNothing().when(doctorService).blockDoctor(Mockito.anyString(), Mockito.anyBoolean());

        mockMvc.perform(post("http://localhost:6969/doctor/blocked?doctorEmail=\"doctorEmail\"&isActive=true&isBlocked=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void fetchPrescriptionByPatientIdTest() throws Exception {

        List<DoctorModel> mockListDoctors = new ArrayList<DoctorModel>();
        Mockito.when(doctorService.fetchDoctor(Mockito.anyLong())).thenReturn(Optional.of(mockListDoctors));

        mockMvc.perform(get("http://localhost:6969/doctor/fetch/{patientId}",100)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void findDoctorOnDetailsWithCityTest() throws Exception {

        List<Map<String, Object>> mockListDoctors = new ArrayList<>();
        Mockito.when(doctorService.findDoctorOnDetailsWithCity(Mockito.any(),Mockito.anyBoolean())).thenReturn(mockListDoctors);

        mockMvc.perform(post("http://localhost:6969/doctor//get_doctor_on_details_and_city",100)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{  \"doctorName\": \"tapan\" , \"doctorQualification\": \"\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor by details found successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
