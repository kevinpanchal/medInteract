package com.csci5308.medinteract.doctor.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name="doctor")
public class DoctorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_generator")
    @SequenceGenerator(name="doctor_generator", sequenceName = "doctor_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique=true)
    private String doctorEmail;

    @NotBlank
    @NotNull
    private String doctorName;
    @NotBlank
    @NotNull
    private Long doctorAddressProvince;
    private String doctorAddressPostalCode;

    private Long doctorAddressCity;
    @NotBlank
    @NotNull
    private String doctorAddressStreet;

    @NotBlank
    @NotNull
    private char doctorGender;

    @DateTimeFormat
    @NotBlank
    @NotNull
    private Date doctorDOB;

    @Digits(integer = 10, fraction = 0)
    @NotBlank
    @NotNull
    private String doctorMobileNumber;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @NotBlank
    @NotNull
    private String profilePicture;

    public Long getDoctorAddressCity() {
        return doctorAddressCity;
    }

    public void setDoctorAddressCity(Long doctorAddressCity) {
        this.doctorAddressCity = doctorAddressCity;
    }

    private boolean isActive;
    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    private boolean isBlocked;
    @NotBlank
    private String doctorType;

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    private String doctorQualification;
    @NotBlank
    @NotNull
    private String doctorPassword;

    private String emailToken;

    public DoctorModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getDoctorAddressProvince() {
        return doctorAddressProvince;
    }

    public void setDoctorAddressProvince(Long doctorAddressProvince) {
        this.doctorAddressProvince = doctorAddressProvince;
    }

    public String getDoctorAddressPostalCode() {
        return doctorAddressPostalCode;
    }

    public void setDoctorAddressPostalCode(String doctorAddressPostalCode) {
        this.doctorAddressPostalCode = doctorAddressPostalCode;
    }

    public String getDoctorAddressStreet() {
        return doctorAddressStreet;
    }

    public void setDoctorAddressStreet(String doctorAddressStreet) {
        this.doctorAddressStreet = doctorAddressStreet;
    }

    public char getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(char doctorGender) {
        this.doctorGender = doctorGender;
    }

    public Date getDoctorDOB() {
        return doctorDOB;
    }

    public void setDoctorDOB(Date doctorDOB) {
        this.doctorDOB = doctorDOB;
    }

    public String getDoctorMobileNumber() {
        return doctorMobileNumber;
    }

    public void setDoctorMobileNumber(String doctorMobileNumber) {
        this.doctorMobileNumber = doctorMobileNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDoctorQualification() {
        return doctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.doctorQualification = doctorQualification;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getEmailToken() {
        return emailToken;
    }

//    public void setEmailToken(String emailToken) {
//        this.emailToken = emailToken;
//    }
}
