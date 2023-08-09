package com.csci5308.medinteract.medicine.service;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import org.springframework.stereotype.Service;

public interface MedicineService {


    MedicineModel saveMedicine(MedicineModel medicineModel);
    MedicineModel findMedicineById(Long medicineId);

    Iterable<MedicineModel> findAllMedicine();
}
