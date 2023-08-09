package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.patient.service.PatientServiceImpl;
import com.csci5308.medinteract.JWT.JWT;
import com.csci5308.medinteract.Response.Response;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.csci5308.medinteract.FileUpload.FileUploader.saveFile;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientServiceImpl;
    private final JWT jwtTokenUtil;

    @Autowired
    public PatientController(PatientService patientServiceImpl, JWT jwtTokenUtil, PatientService patientService, PatientServiceImpl patientServiceImp){
        this.patientServiceImpl = patientServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<PatientModel> patientModelList= patientServiceImpl.fetchAll();
        Response  res = new Response(patientModelList, false, "All Patients Fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity registerPatient(@RequestBody PatientModel patientModel) throws Exception {
        Map<String, Object> data = patientServiceImpl.checkIfEmailExists(patientModel.getPatientEmail());

        if((Boolean)data.get("result"))
        {
            //patient already exists
            Response  res = new Response(null, true, "Patient with email already exists!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);

        }
        else
        {
            if(data.containsKey("id")){
                patientModel.setId((Long)data.get("id"));
            }
            patientModel.setPatientPassword(patientServiceImpl.encodePassword(patientModel.getPatientPassword()));
            patientServiceImpl.savePatient(patientModel);
            patientModel.setPatientPassword("");
            Response res = new Response(patientModel, false, "User registered Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
    @PostMapping("/login")

    public ResponseEntity login(@RequestBody PatientModel patientModel) throws Exception {
        if(patientServiceImpl.isPatientValid(patientModel.getPatientEmail(),patientModel.getPatientPassword()))
        {
            patientModel = patientServiceImpl.getPatientByEmail(patientModel.getPatientEmail());
            patientModel.setPatientPassword("");
            Response  res = new Response(jwtTokenUtil.generateToken(patientModel.getPatientEmail(),"patient",patientModel), false, "Patient logged in Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Failed to login for the given credentials");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }

    @GetMapping("/profile/{patientId}")
    public ResponseEntity getPatientById(@PathVariable("patientId") Long id){
        Optional<PatientModel> patientModel= patientServiceImpl.getPatientById(id);
        if(patientModel.isEmpty() || patientModel.get().isBlocked() || !patientModel.get().isActive()) {
            Response res = new Response("", true, "Unable to find patient with the given id!");
            return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        patientModel.get().setPatientPassword("");
        Response  res = new Response(patientModel, false, "Patient details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping(path = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updatePatientById(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws IOException {
        //get patientModel from Json
        Gson gson = new Gson();
        PatientModel updatedPatientModel = gson.fromJson(formData.getFirst("objectData"), PatientModel.class);
        Optional<PatientModel> optionalPatientModel= patientServiceImpl.getPatientById(updatedPatientModel.getId());
        if(optionalPatientModel.isEmpty() || !optionalPatientModel.get().getPatientEmail().equals(updatedPatientModel.getPatientEmail()))
        {
            //patient already exists
            Response res = new Response(null, true, "Unable to update profile!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        PatientModel oldPatientModel = optionalPatientModel.get();
        updatedPatientModel.setPatientPassword(oldPatientModel.getPatientPassword());
        updatedPatientModel.setActive(oldPatientModel.isActive());
        updatedPatientModel.setBlocked(oldPatientModel.isBlocked());
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName;
            if (oldPatientModel.getProfilePicture()!= null) {
                fileName = oldPatientModel.getProfilePicture().split("/")[2];
            } else {
                fileName = RandomStringUtils.randomAlphanumeric(10) + ".jpeg";
            }
            String uploadDir = "user-photos/profile/";
            try {
                saveFile(uploadDir, fileName, profileImage);
            } catch (IOException e) {
                Response res = new Response("", true, "Error while updating details!");
                return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
            }
            updatedPatientModel.setProfilePicture(uploadDir + fileName);
        } else {
            updatedPatientModel.setProfilePicture(oldPatientModel.getProfilePicture());
        }
        patientServiceImpl.savePatient(updatedPatientModel);
        updatedPatientModel.setPatientPassword("");
        Response  res = new Response(updatedPatientModel, false, "User updated Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @GetMapping("/patientAppointment/{doctorId}")
    public ResponseEntity fetchPatientsWithAppointment(@PathVariable ("doctorId") Long id) {
        Optional<List<PatientModel>> patientModelList = patientServiceImpl.fetchPatientsWithAppointment(id);
        Response res = new Response(patientModelList, false, "Patients fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
