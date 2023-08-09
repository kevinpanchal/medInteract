package com.csci5308.medinteract.medicine.service;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.medicine.service.MedicineService;
import org.springframework.stereotype.Service;

@Service
public class MedicineServiceImpl implements MedicineService {



    private final MedicineRepository medicineRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public MedicineModel saveMedicine(MedicineModel medicineModel) {
        return medicineRepository.save(medicineModel);
    }

    @Override
    public MedicineModel findMedicineById(Long medicineId) {
        return medicineRepository.getByMedicineId(medicineId);
    }

    @Override
    public Iterable<MedicineModel> findAllMedicine() {
        return medicineRepository.findAll();
    }
}
