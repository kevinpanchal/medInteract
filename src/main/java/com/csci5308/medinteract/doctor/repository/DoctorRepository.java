package com.csci5308.medinteract.doctor.repository;

import com.csci5308.medinteract.doctor.model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel,Long> {
    Optional<DoctorModel> findByDoctorEmail(String doctorEmail);


    List<DoctorModel> findByDoctorAddressCity(Long cityId);
    List<DoctorModel> findByDoctorAddressProvince(Long provinceId);
    List<DoctorModel> findByDoctorNameContaining(String name);
    List<DoctorModel> findByDoctorQualification(String qualification);

    List<DoctorModel> findByDoctorQualificationContaining(String qualification);

    @Query("select d from DoctorModel d where (?1 is null or d.doctorName like %?1%) and (?2 is null OR d.doctorAddressProvince = ?2) and (?3 is null OR d.doctorAddressCity = ?3) and (?4 is null or d.doctorQualification like %?4%)")
    List<DoctorModel> findByDoctorOnDetails(String name, Long province, Long city, String qualification);

    @Query("select d,p,c from DoctorModel d JOIN ProvinceModel p ON d.doctorAddressProvince = p.id JOIN CityModel c ON d.doctorAddressCity = c.id where (?1 is null or d.doctorName like %?1%) and (?2 is null OR d.doctorAddressProvince = ?2) and (?3 is null OR d.doctorAddressCity = ?3) and (?4 is null or d.doctorQualification like %?4%) and d.isActive = true")
    List<Object> findDoctorOnDetailsWithCity(String name, Long province, Long city, String qualification);

    @Query("select d,p,c from DoctorModel d JOIN ProvinceModel p ON d.doctorAddressProvince = p.id JOIN CityModel c ON d.doctorAddressCity = c.id where d.isActive = true")
    List<Object> findDoctorOnDetails();

    @Query("select d,p,c,1,2 from DoctorModel d JOIN ProvinceModel p ON d.doctorAddressProvince = p.id JOIN CityModel c ON d.doctorAddressCity = c.id JOIN FeedbackModel f ON d.id = f.doctorId where  d.doctorName like %?1% and  d.doctorAddressProvince = ?2 and d.doctorAddressCity = ?3 and  d.doctorQualification like %?4%")
    List<Object> findDoctorOnDetailsWithCityAndFeedback(String name, Long province, Long city, String qualification);

    @Modifying
    @Query("Update DoctorModel SET isActive = false WHERE id = ?1")
    void deleteById(Long id);
    
    @Query("select d from DoctorModel d where d.isActive = false and d.isBlocked = false")
    Optional<List<DoctorModel>> findPendingDoctors();

    @Query("select d from DoctorModel d where d.isActive = true and d.isBlocked = false")
    Optional<List<DoctorModel>> findApprovedDoctors();

    @Query("select d from DoctorModel d where d.isBlocked = true")
    Optional<List<DoctorModel>> findBlockedDoctors();

    @Query("SELECT d FROM PatientModel p JOIN PrescriptionModel pr ON p.id = pr.patientId JOIN DoctorModel d ON pr.doctorId = d.id WHERE p.id = ?1")
    Optional<List<DoctorModel>> getDoctorModelById(Long id);
}
