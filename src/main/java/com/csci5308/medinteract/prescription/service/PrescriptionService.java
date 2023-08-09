package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    PrescriptionModel savePrescription(PrescriptionModel prescriptionModel);
//    PrescriptionModel findPrescriptionById(Long prescriptionId);
    Iterable<PrescriptionModel> findAllPrescription();

    Optional<List<PrescriptionModel>> fetchPrescription(Long id);
}
