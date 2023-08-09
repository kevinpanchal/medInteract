package com.csci5308.medinteract.patient.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name="patient")
public class PatientModel {
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_generator")
    @SequenceGenerator(name="patient_generator", sequenceName = "patient_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique=true)
    private String patientEmail;

    @NotBlank
    @NotNull
    private String patientName;
    private @NotBlank @NotNull Long patientAddressProvince;
    private @NotBlank @NotNull Long patientAddressCity;
    private String patientAddressPostalCode;
    @NotBlank
    @NotNull
    private String patientAddressStreet;

    @NotBlank
    @NotNull
    private char patientGender;

    @DateTimeFormat
    @NotBlank
    @NotNull
    private Date patientDOB;

    @Digits(integer = 10, fraction = 0)
    @NotBlank
    @NotNull
    private String patientMobileNumber;

    private boolean isActive;

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    @NotBlank
    @NotNull
    private String patientPassword;

    private String emailToken;
    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    private boolean isBlocked;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @NotBlank
    @NotNull
    private String profilePicture;

    public Long getPatientAddressCity() {
        return patientAddressCity;
    }

    public void setPatientAddressCity(Long patientAddressCity) {
        this.patientAddressCity = patientAddressCity;
    }

    public PatientModel() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public @NotBlank @NotNull Long getPatientAddressProvince() {
        return patientAddressProvince;
    }

    public void setPatientAddressProvince(@NotBlank @NotNull Long patientAddressProvince) {
        this.patientAddressProvince = patientAddressProvince;
    }

    public String getPatientAddressPostalCode() {
        return patientAddressPostalCode;
    }

    public void setPatientAddressPostalCode(String patientAddressPostalCode) {
        this.patientAddressPostalCode = patientAddressPostalCode;
    }

    public String getPatientAddressStreet() {
        return patientAddressStreet;
    }

    public void setPatientAddressStreet(String patientAddressStreet) {
        this.patientAddressStreet = patientAddressStreet;
    }

    public char getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(char patientGender) {
        this.patientGender = patientGender;
    }

    public Date getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(Date patientDOB) {
        this.patientDOB = patientDOB;
    }



    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
