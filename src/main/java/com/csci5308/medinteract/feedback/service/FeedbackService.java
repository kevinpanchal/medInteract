package com.csci5308.medinteract.feedback.service;

import com.csci5308.medinteract.feedback.model.FeedbackModel;

import java.util.List;
import java.util.Map;

public interface FeedbackService {

    //public List<FeedbackModel> fetchAll();
    public void saveFeedback(FeedbackModel feedbackModel);

    //public void deleteAll();

    //public List<FeedbackModel> fetchFeedbackByDoctorId(FeedbackModel feedbackModel);

    public List<Map<String, Object>> fetchFeedbackByDoctorIdAndPatient(FeedbackModel feedbackModel);

    public List<Map<String, Object>> findAvgRatingOfDoctor();


}
