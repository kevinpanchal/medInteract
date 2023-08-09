package com.csci5308.medinteract.appointment.repository;

import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {
    @Query("SELECT a FROM AppointmentModel a WHERE a.patientId = ?1 AND a.isActive = true")
    List<AppointmentModel> findByPatientId(Long patientId);

    @Query("SELECT a FROM AppointmentModel a WHERE a.doctorId = ?1 AND a.isActive = true")
    List<AppointmentModel> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM AppointmentModel a WHERE a.patientId = ?1 AND a.isActive = true AND a.startTime>=?2")
    List<AppointmentModel> fetchAppointmentsByPatientAfterDate(Long patientId, LocalDateTime date);

    @Query("SELECT a,p,d FROM AppointmentModel a JOIN DoctorModel d ON d.id = a.doctorId JOIN PatientModel p ON p.id = a.patientId WHERE TIME_TO_SEC(TIMEDIFF(a.startTime, ?1)) > 0 AND TIME_TO_SEC(TIMEDIFF(a.startTime, ?1)) <= 3600 AND a.isActive = true")
    List<Object> findHourlyAppointments(LocalDateTime localDate);

    @Query("SELECT a,p,d FROM AppointmentModel a JOIN DoctorModel d ON d.id = a.doctorId JOIN PatientModel p ON p.id = a.patientId WHERE DATEDIFF(a.startTime, ?1) = 0 AND a.isActive = true")
    List<Object> findDailyAppointments(LocalDateTime localDate);

    // @Query("SELECT a,p,d FROM AppointmentModel a JOIN DoctorModel d ON d.id =
    // a.doctorId JOIN PatientModel p ON p.id = a.patientId WHERE DATE(a.startTime)
    // = DATE(?1) AND a.isActive = true")
    // List<Object> findDailyAppointments(LocalDateTime localDate);

    @Query("SELECT a,p,d FROM AppointmentModel a JOIN DoctorModel d ON d.id = a.doctorId JOIN PatientModel p ON p.id = a.patientId WHERE DATEDIFF(a.startTime, ?1) = 3 AND a.isActive = true")
    List<Object> findThreeDaysAppointments(LocalDateTime localDate);

    @Query("SELECT a,p,d FROM AppointmentModel a JOIN DoctorModel d ON d.id = a.doctorId JOIN PatientModel p ON p.id = a.patientId WHERE DATEDIFF(a.startTime, ?1) = 7 AND a.isActive = true")
    List<Object> findWeeklyAppointments(LocalDateTime localDate);

    @Query("SELECT DISTINCT d FROM AppointmentModel a JOIN DoctorModel d ON d.id = a.doctorId WHERE a.patientId = ?1")
    List<DoctorModel> doctorNameByPatientAppointments(Long patientId);
}
