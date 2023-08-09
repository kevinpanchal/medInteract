package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import com.csci5308.medinteract.patient.model.PatientModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AppointmentService.class)
public class AppoinmentServiceTest {
    @MockBean
    @Autowired
    private AppointmentRepository mockappointmentRepository;

    @Autowired
    private AppointmentService appointmentService;


    private AppointmentModel mockAppoinmentModel = new AppointmentModel();;

    @Test
    void saveAppointmentTest() throws Exception {

        Mockito.when(mockappointmentRepository.save(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);

        assertEquals(mockAppoinmentModel,appointmentService.saveAppointment(mockAppoinmentModel));

    }

    @Test
    void fetchAppointmentsByDoctorTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(mockappointmentRepository.findByDoctorId(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByDoctor(101l));

    }

    @Test
    void fetchAppointmentsByPatientTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(mockappointmentRepository.findByPatientId(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByPatient(101l));

    }

    @Test
    void fetchAppointmentsByPatientAfterDateTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        LocalDateTime now = LocalDateTime.now();

        Mockito.when(mockappointmentRepository.fetchAppointmentsByPatientAfterDate(Mockito.anyLong(), Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByPatientAfterDate(101l, now));

    }

    @Test
    void fetchAppointmentsHourlyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findHourlyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsHourly());

    }

    @Test
    void fetchAppointmentsDailyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findDailyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsDaily());

    }

    @Test
    void fetchAppointmentsWithinThreeDaysTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findThreeDaysAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsWithinThreeDays());

    }

    @Test
    void fetchAppointmentsWeeklyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findWeeklyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsWeekly());

    }

    @Test
    void fetchDoctorNamesByPatientsAppointmentTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(doctorModel);

        Mockito.when(mockappointmentRepository.doctorNameByPatientAppointments(Mockito.anyLong())).thenReturn(mockDoctorModelList);

        assertEquals(mockDoctorModelList,appointmentService.fetchDoctorNamesByPatientsAppointment(201l));

    }
}
