package com.csci5308.medinteract.medicine.controller;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.medicine.service.MedicineService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }


    @PostMapping("/addMedicines")
    public ResponseEntity addMedicines(@RequestBody MedicineModel medicineModel) throws Exception {
        medicineService.saveMedicine(medicineModel);
        Response res = new Response(medicineModel, false, "Medicines added Successfully!");
        return  new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    public Iterable<MedicineModel> fetchAll() {
        return medicineService.findAllMedicine();
    }

    @GetMapping("/fetch/{medicineId}")
    public ResponseEntity getMedicineById(@PathVariable("medicineId") Long id){
        Optional<MedicineModel> medicineModel = Optional.ofNullable(medicineService.findMedicineById(id));
        Response  res = new Response(medicineModel, false, "Medicines details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}
