package com.csci5308.medinteract.medicine.service;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.prescription.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MedicineService.class)
public class MedicineServiceTest {

    @MockBean
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineService medicineService;

    @BeforeEach
    public void setUp() {
        medicineService = new MedicineServiceImpl(medicineRepository);
    }

    @Test
    public void saveMedicineTest() {
        MedicineModel medicineModel = new MedicineModel();
        medicineModel.setMedicineName("Paracetamol");
        medicineModel.setMedicineAmount(1);
        when(medicineRepository.save(any(MedicineModel.class))).thenReturn(medicineModel);

        MedicineModel savedMedicine = medicineService.saveMedicine(medicineModel);

        assertEquals("Paracetamol", savedMedicine.getMedicineName());
        verify(medicineRepository, times(1)).save(any(MedicineModel.class));
    }

    @Test
    public void findMedicineByIdTest() {
        MedicineModel medicineModel = new MedicineModel();
        medicineModel.setMedicineId(1L);
        medicineModel.setMedicineName("Paracetamol");
        medicineModel.setMedicineAmount(1);
        when(medicineRepository.getByMedicineId(1L)).thenReturn(medicineModel);

        MedicineModel foundMedicine = medicineService.findMedicineById(1L);

        assertEquals("Paracetamol", foundMedicine.getMedicineName());
        verify(medicineRepository, times(1)).getByMedicineId(1L);
    }

    @Test
    public void findAllMedicineTest() {
        MedicineModel medicineModel1 = new MedicineModel();
        medicineModel1.setMedicineId(1L);
        medicineModel1.setMedicineName("Paracetamol");
        medicineModel1.setMedicineAmount(1);

        MedicineModel medicineModel2 = new MedicineModel();
        medicineModel2.setMedicineId(2L);
        medicineModel2.setMedicineName("Ibuprofen");
        medicineModel2.setMedicineAmount(2);

        List<MedicineModel> medicineList = new ArrayList<>();
        medicineList.add(medicineModel1);
        medicineList.add(medicineModel2);
        when(medicineRepository.findAll()).thenReturn(medicineList);

        Iterable<MedicineModel> foundMedicineList = medicineService.findAllMedicine();

        assertEquals(2, ((List<MedicineModel>) foundMedicineList).size());
        verify(medicineRepository, times(1)).findAll();
    }
}
