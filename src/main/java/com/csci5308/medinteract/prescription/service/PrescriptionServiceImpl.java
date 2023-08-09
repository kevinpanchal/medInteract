package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.prescription.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {


    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, MedicineRepository medicineRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public PrescriptionModel savePrescription(PrescriptionModel prescriptionModel) {
        return prescriptionRepository.save(prescriptionModel);
    }

//    @Override
//    public PrescriptionModel findPrescriptionById(Long prescriptionId) {
//        return prescriptionRepository.getByPrescriptionId(prescriptionId);
//    }

    @Override
    public Iterable<PrescriptionModel> findAllPrescription() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<List<PrescriptionModel>> fetchPrescription(Long id) {
        return prescriptionRepository.getPrescriptionModelBy(id);
    }
}
