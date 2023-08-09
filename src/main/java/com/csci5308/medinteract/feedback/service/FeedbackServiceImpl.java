package com.csci5308.medinteract.feedback.service;

import com.csci5308.medinteract.feedback.model.FeedbackModel;
import com.csci5308.medinteract.feedback.repository.FeedbackRepository;
import com.csci5308.medinteract.patient.model.PatientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

//    @Override
//    public List<FeedbackModel> fetchAll() {
//        return feedbackRepository.findAll();
//    }

    public void saveFeedback(FeedbackModel feedbackModel) {
        Date currentDateAndTime = new Date();
        feedbackModel.setFeedbackDate(currentDateAndTime);
        feedbackRepository.save(feedbackModel);
    }

//    public void deleteAll() {
//        feedbackRepository.deleteAll();
//    }

//    public List<FeedbackModel> fetchFeedbackByDoctorId(FeedbackModel feedbackModel) {
//        return feedbackRepository.findByDoctorId(feedbackModel.getDoctorId());
//    }

    public List<Map<String, Object>> fetchFeedbackByDoctorIdAndPatient(FeedbackModel feedbackModel) {
        FeedbackModel feedbackModelCurrentPatient = feedbackRepository
                .findByDoctorIdAndPatientId(feedbackModel.getDoctorId(), feedbackModel.getPatientId());

        List<Object> feedbackModelList = feedbackRepository.findByDoctorIdAndPatient(feedbackModel.getDoctorId());

        List<Map<String, Object>> feedbackDetailsList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        if (feedbackModelCurrentPatient != null) {
            data.put("id", feedbackModelCurrentPatient.getId());
            data.put("feedbackDate", feedbackModelCurrentPatient.getFeedbackDate());
            data.put("rating", feedbackModelCurrentPatient.getRating());
            data.put("comment", feedbackModelCurrentPatient.getComment());
            data.put("doctorId", feedbackModelCurrentPatient.getDoctorId());
            data.put("patientId", feedbackModelCurrentPatient.getPatientId());

        }
        feedbackDetailsList.add(data);


        for (int i = 0; i < feedbackModelList.size(); i++) {
            data = new HashMap<>();
            FeedbackModel feedbackModel1 = (FeedbackModel) (((Object[]) feedbackModelList.get(i))[0]);
            PatientModel patientModel = (PatientModel) (((Object[]) feedbackModelList.get(i))[1]);

            data.put("id", feedbackModel1.getId());
            data.put("feedbackDate", feedbackModel1.getFeedbackDate());
            data.put("rating", feedbackModel1.getRating());
            data.put("comment", feedbackModel1.getComment());
            data.put("doctorId", feedbackModel1.getDoctorId());
            data.put("patientId", feedbackModel1.getPatientId());
            data.put("patientName", patientModel.getPatientName());
            data.put("profilePicture", patientModel.getProfilePicture());

            feedbackDetailsList.add(data);
        }

        return feedbackDetailsList;
    }

    public List<Map<String, Object>> findAvgRatingOfDoctor() {

        List<Object> feedbackModelList = feedbackRepository.findAvgRatingOfDoctor();

        List<Map<String, Object>> feedbackDetailsList = new ArrayList<>();

        for (int i = 0; i < feedbackModelList.size(); i++) {
            Long doctorId = (Long) (((Object[]) feedbackModelList.get(i))[0]);
            Double avgRating = (Double) (((Object[]) feedbackModelList.get(i))[1]);

            Map<String, Object> data = new HashMap<>();

            data.put("doctorId", doctorId);
            data.put("avgRating", avgRating);

            feedbackDetailsList.add(data);
        }

        return feedbackDetailsList;
    }

}
