package com.csci5308.medinteract.doctor.service;

import com.csci5308.medinteract.doctor.model.DoctorModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorService {
    List<DoctorModel> fetchAll();

    DoctorModel saveDoctor(DoctorModel DoctorModel);

    Map<String, Object> checkIfEmailExists(String email);

    DoctorModel getDoctorByEmail(String email);

    boolean isDoctorValid(String DoctorEmail, String DoctorPassword) throws Exception;

    String encodePassword(String password) throws Exception;

    List<Map<String, Object>> findDoctorOnDetailsWithCity(DoctorModel doctorModel, Boolean flag);

    Optional<DoctorModel> getDoctorById(Long id);

    List<DoctorModel> isPending();

    List<DoctorModel> isApproved();

    List<DoctorModel> isBlocked();

    void verifyDoctor(String email, boolean isActive, boolean isBlocked);

    void blockDoctor(String email, boolean isBlocked);

    Optional<List<DoctorModel>> fetchDoctor(Long id);
}
