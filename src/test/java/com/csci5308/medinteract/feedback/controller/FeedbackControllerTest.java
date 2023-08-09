package com.csci5308.medinteract.feedback.controller;

import com.csci5308.medinteract.feedback.model.FeedbackModel;
import com.csci5308.medinteract.feedback.repository.FeedbackRepository;
import com.csci5308.medinteract.feedback.service.FeedbackServiceImpl;
import com.csci5308.medinteract.JWT.JWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private FeedbackRepository feedbackRepository;

    @MockBean
    private JWT jwt;

    @MockBean
    private FeedbackServiceImpl feebackService;
    private FeedbackModel mockFeedbackModel = new FeedbackModel(3,101,201);;
    private String feedbackJSON = "{ \"rating\": 3,\"doctorId\": 101, \"patientId\": 201 }";

    @Test
    void saveFeedbackTest() throws Exception {

        Mockito.doNothing().when(feebackService).saveFeedback(Mockito.any(FeedbackModel.class));


        mockMvc.perform(post("http://localhost:6969/feedback/saveFeedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Feedback added Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchFeedbackByDoctorIdAndPatientTest() throws Exception {

        List<Map<String, Object>> mockFeedbackDetailsList = new ArrayList<>();
        Mockito.when(feebackService.fetchFeedbackByDoctorIdAndPatient(Mockito.any(FeedbackModel.class))).thenReturn(mockFeedbackDetailsList);


        mockMvc.perform(post("http://localhost:6969/feedback/fetchFeedback_by_doctorId_and_patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Feedback fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void findAvgRatingOfDoctor() throws Exception {

        List<Map<String, Object>> mockFeedbackDetailsList = new ArrayList<>();
        Mockito.when(feebackService.findAvgRatingOfDoctor()).thenReturn(mockFeedbackDetailsList);


        mockMvc.perform(get("http://localhost:6969/feedback/fetchAvgFeedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Avg Feedback fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }
}
