package com.csci5308.medinteract.medicine.model;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "medicine")
public class MedicineModel {
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_generator")
    @SequenceGenerator(name="medicine_generator", sequenceName = "medicine_seq", allocationSize=100)
    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    public MedicineModel() {
    }

    @NotBlank
    @NotNull
    private String medicineName;

    @NotBlank
    @NotNull
    private int medicineAmount;

    private boolean isMorning;

    private boolean isAfternoon;

    private boolean isEvening;

    private String additionalNotes;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(int medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(boolean morning) {
        isMorning = morning;
    }

    public boolean isAfternoon() {
        return isAfternoon;
    }

    public void setAfternoon(boolean afternoon) {
        isAfternoon = afternoon;
    }

    public boolean isEvening() {
        return isEvening;
    }

    public void setEvening(boolean evening) {
        isEvening = evening;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long prescriptionId) {
        this.medicineId = prescriptionId;
    }

    public MedicineModel(Long medicineId, String medicineName, int medicineAmount, boolean isMorning, boolean isAfternoon, boolean isEvening, String additionalNotes) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineAmount = medicineAmount;
        this.isMorning = isMorning;
        this.isAfternoon = isAfternoon;
        this.isEvening = isEvening;
        this.additionalNotes = additionalNotes;
    }
}
