package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.service.PrescriptionService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/addPrescription")
    public ResponseEntity addPrescription(@RequestBody PrescriptionModel prescriptionModel) throws Exception {
            prescriptionService.savePrescription(prescriptionModel);
            Response  res = new Response(prescriptionModel, false, "Prescription added Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public Iterable<PrescriptionModel> fetchAll() {
        return prescriptionService.findAllPrescription();
    }

    @GetMapping("/fetch/{patientId}")
    public ResponseEntity fetchPrescriptionByPatientId(@PathVariable("patientId") Long id){
        Optional<List<PrescriptionModel>> prescriptionModelList = prescriptionService.fetchPrescription(id);
        Response  res = new Response(prescriptionModelList, false, "Prescription details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}
