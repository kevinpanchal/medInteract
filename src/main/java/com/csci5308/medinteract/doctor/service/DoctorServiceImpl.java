package com.csci5308.medinteract.doctor.service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.repository.DoctorRepository;
import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.PasswordManager.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<DoctorModel> fetchAll() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorModel saveDoctor(DoctorModel doctorModel) {
        return doctorRepository.save(doctorModel);
    }

    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        boolean result = newDoctor.isPresent() && (newDoctor.get().isActive() || newDoctor.get().isBlocked());
        res.put("result", result);
        if (newDoctor.isPresent()) {
            res.put("id", newDoctor.get().getId());
            res.put("data", newDoctor.get());
        }
        return res;
    }

    @Override
    public DoctorModel getDoctorByEmail(String email) {
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        return newDoctor.orElse(null);
    }

    @Override
    public boolean isDoctorValid(String doctorEmail, String doctorPassword) throws Exception {
        Optional<DoctorModel> doctor = doctorRepository.findByDoctorEmail(doctorEmail);
        String encodedPassword = encodePassword(doctorPassword);
        boolean isValid = doctor.isPresent() && doctor.get().getDoctorPassword().equals(encodedPassword) && doctor.get().isActive()
        && !doctor.get().isBlocked();
        if (isValid) {
            // valid doctor
            return true;
        }
        return false;
    }

    @Override
    public String encodePassword(String password) throws Exception {
        return PasswordEncodeDecode.encrypt(password);
    }

    @Override
    public Optional<DoctorModel> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public List<DoctorModel> isPending() {
        Optional<List<DoctorModel>> pendingDoctorList;
        pendingDoctorList = doctorRepository.findPendingDoctors();
        return pendingDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isApproved() {
        Optional<List<DoctorModel>> approvedDoctorList;
        approvedDoctorList = doctorRepository.findApprovedDoctors();
        return approvedDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isBlocked() {
        Optional<List<DoctorModel>> blockedDoctorList;
        blockedDoctorList = doctorRepository.findBlockedDoctors();
        return blockedDoctorList.orElse(null);
    }

    @Override
    public void verifyDoctor(String email, boolean isActive, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setActive(isActive);
            doctor.setBlocked(!isBlocked);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public void blockDoctor(String email, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setBlocked(isBlocked);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public Optional<List<DoctorModel>> fetchDoctor(Long id) {
        return doctorRepository.getDoctorModelById(id);
    }

    @Override
    public List<Map<String, Object>> findDoctorOnDetailsWithCity(DoctorModel doctorModel, Boolean flag) {
        String name = doctorModel.getDoctorName();
        Long province = doctorModel.getDoctorAddressProvince();
        Long city = doctorModel.getDoctorAddressCity();
        String qualification = doctorModel.getDoctorQualification();
        List<Object> doctorModelList;
        if(!flag) {
            doctorModelList = doctorRepository.findDoctorOnDetails();
        } else {
            doctorModelList = doctorRepository.findDoctorOnDetailsWithCity(name, province, city,
                    qualification);
        }

        List<Map<String, Object>> doctorDetailsList = new ArrayList<>();

        for (int i = 0; i < doctorModelList.size(); i++) {
            DoctorModel doctorModel1 = (DoctorModel) (((Object[]) doctorModelList.get(i))[0]);
            ProvinceModel provinceModel = (ProvinceModel) (((Object[]) doctorModelList.get(i))[1]);
            CityModel cityModel = (CityModel) (((Object[]) doctorModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();

            data.put("id", doctorModel1.getId());
            data.put("doctorEmail", doctorModel1.getDoctorEmail());
            data.put("doctorName", doctorModel1.getDoctorName());
            data.put("doctorAddressProvince", provinceModel.getName());
            data.put("doctorAddressCity", cityModel.getCity());
            data.put("doctorType", doctorModel1.getDoctorType());
            data.put("doctorQualification", doctorModel1.getDoctorQualification());
            data.put("profilePhoto", doctorModel1.getProfilePicture());

            doctorDetailsList.add(data);
        }

        return doctorDetailsList;
    }
}
