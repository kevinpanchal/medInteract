package com.csci5308.medinteract.feedback.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="feedback")
public class FeedbackModel {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_generator")
    @SequenceGenerator(name="feedback_generator", sequenceName = "feedback_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;

    @DateTimeFormat
    @NotBlank
    @NotNull
    private Date feedbackDate;

    @NotBlank
    @NotNull
    private long rating;

    private String comment;

    @NotBlank
    @NotNull
    private long doctorId;

    @NotBlank
    @NotNull
    private long patientId;

    public FeedbackModel(long rating, long doctorId, long patientId)
    {
        this.rating = rating;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public FeedbackModel() {
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
}
