package com.csci5308.medinteract.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTestIT {

    static String failedMsg = "Admin log-in Failed!";
    static String successMsg = "User logged in Successfully!";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginSuccess() throws Exception {
            mockMvc.perform(post("/admin/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"adminEmail\": \"admin\",\"adminPassword\": \"Group27\" }"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value(successMsg))
                    .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void loginFailed() throws Exception {
        mockMvc.perform(post("http://localhost:6969/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"adminEmail\": \"admin\",\"adminPassword\": \"Group7\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(failedMsg))
                .andExpect(jsonPath("$.isError").value("true"));
    }
}
