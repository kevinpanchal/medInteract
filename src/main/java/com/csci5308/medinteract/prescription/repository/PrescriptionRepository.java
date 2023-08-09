package com.csci5308.medinteract.prescription.repository;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionModel,Long> {
    PrescriptionModel getByPrescriptionId(Long prescriptionId);

    @Query("SELECT pr FROM PatientModel p JOIN PrescriptionModel pr ON p.id = pr.patientId JOIN DoctorModel d ON pr.doctorId = d.id WHERE p.id = ?1")
    Optional<List<PrescriptionModel>> getPrescriptionModelBy(Long id);

}
