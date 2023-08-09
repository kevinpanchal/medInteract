package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.PasswordManager.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientModel> fetchAll() {
        return patientRepository.findAll();
    }

    @Override
    public PatientModel savePatient(PatientModel patientModel) {
        return patientRepository.save(patientModel);
    }

    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(email);
        boolean result = newPatient.isPresent() && (newPatient.get().isActive() || newPatient.get().isBlocked());
        res.put("result", result);
        if (newPatient.isPresent()) {
            res.put("id", newPatient.get().getId());
        }
        return res;
    }

    @Override
    public PatientModel getPatientByEmail(String email) {
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(email);
        return newPatient.orElse(null);
    }

    @Override
    public boolean isPatientValid(String patientEmail, String patientPassword) throws Exception {
        Optional<PatientModel> patient = patientRepository.findByPatientEmail(patientEmail);

        String encodedPassword = encodePassword(patientPassword);
        boolean isValid = patient.isPresent() && patient.get().getPatientPassword().equals(encodedPassword) && patient.get().isActive() && !patient.get().isBlocked();
        if (isValid) {
            //valid patient
            return true;
        }
        return false;
    }

    @Override
    public String encodePassword(String password) throws Exception {
        return PasswordEncodeDecode.encrypt(password);
    }

    @Override
    public Optional<PatientModel> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    @Override
    public Optional<List<PatientModel>> fetchPatientsWithAppointment(Long id) {
        return patientRepository.findByPatientId(id);
    }
}
