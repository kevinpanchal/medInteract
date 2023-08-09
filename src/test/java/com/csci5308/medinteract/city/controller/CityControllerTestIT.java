package com.csci5308.medinteract.city.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchAll() throws Exception {

        mockMvc.perform(get("http://localhost:6969/city/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All cities fetched successfully"))
                .andExpect(jsonPath("$.isError").value("false"));    }

    @Test
    void getCityId() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("city", "Toronto");

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/city/city_id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("City Id found"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void getCityName() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("id", 1);

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/city/city_name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("City name found"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void getCityIdWithProvince() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("province_mapping", 1);

        String json = obj.toString();

        mockMvc.perform(post("http://localhost:6969/city/city_name_with_province")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("City Id found"))
                .andExpect(jsonPath("$.isError").value("false"));
    }
}