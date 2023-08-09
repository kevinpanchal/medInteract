package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {
    List<PatientModel> fetchAll();

    PatientModel savePatient(PatientModel patientModel);

    Map<String, Object> checkIfEmailExists(String email);

    PatientModel getPatientByEmail(String email);

    boolean isPatientValid(String patientEmail, String patientPassword) throws Exception;

    String encodePassword(String password) throws Exception;

    Optional<PatientModel> getPatientById(Long id);

    Optional<List<PatientModel>> fetchPatientsWithAppointment(Long id);
}
