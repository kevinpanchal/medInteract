package com.csci5308.medinteract.notification.controller;

import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(NotificationController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    public void testFetchAll() throws Exception {
        List<NotificationModel> notificationModelList = new ArrayList<>();
        notificationModelList.add(new NotificationModel("Doctor", 1L, "Appointment with Patient at 4pm", "Appointment Reminder", "/appointments/1", "success", LocalDateTime.now()));
        notificationModelList.add(new NotificationModel("Doctor", 1L, "Follow-up with Patient scheduled for tomorrow", "Follow-up Reminder", "/appointments/2", "warning", LocalDateTime.now()));

        when(notificationService.fetchAll(1L, "Doctor")).thenReturn(notificationModelList);

        String requestJson = "{\"userType\": \"Doctor\", \"userId\": 1}";

        mockMvc.perform(MockMvcRequestBuilders.post("/notification/fetchAll")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All notifications fetched successfully"))
                .andExpect(jsonPath("$.isError").value("false"));
    }
}
