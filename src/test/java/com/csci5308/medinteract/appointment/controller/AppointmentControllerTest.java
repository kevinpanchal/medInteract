package com.csci5308.medinteract.appointment.controller;

import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import com.csci5308.medinteract.appointment.service.AppointmentServiceImpl;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.service.DoctorService;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.JWT.JWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.csci5308.medinteract.notification.service.NotificationService;

import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AppointmentController.class)
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AppointmentRepository appointmentRepository;

    @MockBean
    private JWT jwt;

    @MockBean
    private AppointmentServiceImpl appointmentService;

    @MockBean
    private DoctorService doctorService;
    @MockBean
    private PatientService patientService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;
    private AppointmentModel mockAppoinmentModel = new AppointmentModel();

    @Test
    void registerAppointmentTest() throws Exception {

        DoctorModel mockDoctorModel = new DoctorModel();
        mockDoctorModel.setId(101l);

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(Optional.of(mockDoctorModel));
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointment registered Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void registerAppointmentFailedTest() throws Exception {

        Optional<DoctorModel> mockDoctorModel = Optional.empty();

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(mockDoctorModel);
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("An unknown error occurred!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void updateAppointmentTest() throws Exception {

        DoctorModel mockDoctorModel = new DoctorModel();
        mockDoctorModel.setId(101l);

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(Optional.of(mockDoctorModel));
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointment updated Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void updateAppointmentFailedTest() throws Exception {

        Optional<DoctorModel> mockDoctorModel = Optional.empty();

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(mockDoctorModel);
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("An unknown error occurred!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void deleteAppointmentTest() throws Exception {

        DoctorModel mockDoctorModel = new DoctorModel();
        mockDoctorModel.setId(101l);

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(Optional.of(mockDoctorModel));
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointment deleted Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void deleteAppointmentFailedTest() throws Exception {

        Optional<DoctorModel> mockDoctorModel = Optional.empty();

        PatientModel mockPatientModel = new PatientModel();
        mockPatientModel.setId(201l);

        Mockito.when(appointmentService.saveAppointment(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);
        Mockito.when(doctorService.getDoctorById(Mockito.anyLong())).thenReturn(mockDoctorModel);
        Mockito.when(patientService.getPatientById(Mockito.anyLong())).thenReturn(Optional.of(mockPatientModel));

        mockMvc.perform(post("http://localhost:6969/appointment/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 301, \"patientId\": 201, \"doctorId\": 101, \"startTime\": \"2023-04-03T11:27:30.123456789\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("An unknown error occurred!"))
                .andExpect(jsonPath("$.isError").value("true"));

    }

    @Test
    void fetchAppointmentsByDoctorTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(appointmentService.fetchAppointmentsByDoctor(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"doctorId\": 101 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchDoctorNamesByAppointmentsTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();

        Mockito.when(appointmentService.fetchDoctorNamesByPatientsAppointment(Mockito.anyLong())).thenReturn(mockDoctorModelList);

        mockMvc.perform(post("http://localhost:6969/appointment/fetchDoctorNamesByAppointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\": 101 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctors fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchAppointmentsByPatientTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(appointmentService.fetchAppointmentsByPatient(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByPatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\": 101 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchAppointmentsByPatientAfterDateTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(appointmentService.fetchAppointmentsByPatientAfterDate(Mockito.anyLong(), Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByPatientAfterDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientId\": 101 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

}
