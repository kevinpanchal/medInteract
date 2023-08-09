package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.repository.PrescriptionRepository;
import com.csci5308.medinteract.prescription.service.PrescriptionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PrescriptionController.class)
public class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PrescriptionController prescriptionController;

    @MockBean
    private PrescriptionService prescriptionService;

    private String url = "http://localhost:6969/prescription/";

    @Test
    public void testFetchAllPrescriptions() throws Exception {
        PrescriptionModel prescription1 = new PrescriptionModel();
        prescription1.setId(1L);
        prescription1.setPatientId(1L);
        prescription1.setDoctorId(1L);
        prescription1.setPrescriptionTime(LocalDateTime.now());
        prescription1.setMedicines(Collections.singletonList(new MedicineModel()));

        PrescriptionModel prescription2 = new PrescriptionModel();
        prescription2.setId(2L);
        prescription2.setPatientId(2L);
        prescription2.setDoctorId(2L);
        prescription2.setPrescriptionTime(LocalDateTime.now());
        prescription2.setMedicines(Collections.singletonList(new MedicineModel()));

        List<PrescriptionModel> prescriptionList = Arrays.asList(prescription1, prescription2);

        Mockito.when(prescriptionService.findAllPrescription()).thenReturn(prescriptionList);

        mockMvc.perform(get("/prescription/fetchAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchPrescriptionByPatientId() throws Exception {
        Long patientId = 101L;
        List<PrescriptionModel> prescriptionModelList = new ArrayList<>();
        prescriptionModelList.add(new PrescriptionModel());
        Optional<List<PrescriptionModel>> optionalList = Optional.of(prescriptionModelList);
        Mockito.when(prescriptionService.fetchPrescription(patientId)).thenReturn(optionalList);

        mockMvc.perform(get(url + "fetch/" + patientId)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Prescription details fetched Successfully!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isError").value("false"));
    }

    @Test
    public void testAddPrescription() throws Exception {
        PrescriptionModel prescriptionModel = new PrescriptionModel();
        prescriptionModel.setPatientId(1L);
        prescriptionModel.setDoctorId(2L);
        LocalDateTime prescriptionTime = LocalDateTime.now();
        prescriptionModel.setPrescriptionTime(prescriptionTime);
        List<MedicineModel> medicineModels = new ArrayList<>();
        MedicineModel medicineModel = new MedicineModel();
        medicineModel.setMedicineName("Medicine 1");
        medicineModels.add(medicineModel);
        prescriptionModel.setMedicines(medicineModels);

        Mockito.when(prescriptionService.savePrescription(any(PrescriptionModel.class)))
                .thenAnswer((Answer<?>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return (PrescriptionModel) args[0];
                });
    }

}
