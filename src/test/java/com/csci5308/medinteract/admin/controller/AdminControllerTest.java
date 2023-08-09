package com.csci5308.medinteract.admin.controller;

import com.csci5308.medinteract.admin.model.AdminModel;
import com.csci5308.medinteract.admin.repository.AdminRepository;
import com.csci5308.medinteract.admin.service.AdminService;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AdminRepository adminRepository;

    @MockBean
    private JWT jwt;

    @MockBean
    private AdminService adminService;
    private AdminModel mockAdminModel = new AdminModel("admin","Group27");;
    private String adminJSON = "{ \"adminEmail\": \"admin\",\"adminPassword\": \"Group27\" }";
    @Test
    void loginTest() throws Exception {

        Mockito.when(adminService.isAdminValid(Mockito.anyString(),
                Mockito.anyString())).thenReturn(true);

        mockMvc.perform(post("http://localhost:6969/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"adminEmail\": \"admin\",\"adminPassword\": \"Group27\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("User logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

        boolean serviceAdminValid = adminService.isAdminValid(mockAdminModel.getAdminEmail(), mockAdminModel.getAdminPassword());

        assertTrue(serviceAdminValid);


    }
//       verify(adminRepository).findAll();

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