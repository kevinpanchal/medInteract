package com.csci5308.medinteract.feedback.repository;

import com.csci5308.medinteract.feedback.model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

    List<FeedbackModel> findByDoctorId(Long doctorId);

    @Query("select f,p from FeedbackModel f JOIN PatientModel p ON f.patientId = p.id where f.doctorId = ?1 ORDER BY f.feedbackDate DESC")
    List<Object> findByDoctorIdAndPatient(Long doctorID);

    @Query("SELECT f.doctorId, AVG(f.rating) FROM FeedbackModel f GROUP by f.doctorId")
    List<Object> findAvgRatingOfDoctor();


    FeedbackModel findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
