package com.csci5308.medinteract.appointment.controller;

import com.csci5308.medinteract.appointment.service.AppointmentService;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.service.DoctorService;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.service.NotificationService;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentServiceImpl;
    private final DoctorService doctorServiceImpl;
    private final PatientService patientServiceImpl;

    private final NotificationService notificationServiceImpl;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public AppointmentController(AppointmentService appointmentServiceImpl, DoctorService doctorServiceImpl,
            PatientService patientServiceImpl, NotificationService notificationServiceImpl) {
        this.appointmentServiceImpl = appointmentServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity registerAppointment(@RequestBody AppointmentModel appointmentModel) throws Exception {
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(appointmentModel.getDoctorId());
        Optional<PatientModel> patientModel = patientServiceImpl.getPatientById(appointmentModel.getPatientId());
        if (doctorModel.isEmpty() || patientModel.isEmpty()) {
            Response res = new Response("", true, "An unknown error occurred!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        appointmentServiceImpl.saveAppointment(appointmentModel);
        Map<String, Object> data = new HashMap<>();
        data.put("type", "appointment");
        data.put("id", appointmentModel.getId().toString());
        data.put("action", "create");
        data.put("data", appointmentModel);
        data.put("message",
                "Your appointment with " + doctorModel.get().getDoctorName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is created!");
        NotificationModel notificationModel = new NotificationModel("patient", appointmentModel.getPatientId(),
                data.get("message").toString(), "Appointment Created", "", "success", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
        data.put("message",
                "Your appointment with " + patientModel.get().getPatientName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is created!");
        notificationModel = new NotificationModel("doctor", appointmentModel.getDoctorId(),
                data.get("message").toString(), "Appointment Created", "", "success", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        Response res = new Response(appointmentModel, false, "Appointment registered Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateAppointment(@RequestBody AppointmentModel appointmentModel) throws Exception {
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(appointmentModel.getDoctorId());
        Optional<PatientModel> patientModel = patientServiceImpl.getPatientById(appointmentModel.getPatientId());
        if (doctorModel.isEmpty() || patientModel.isEmpty()) {
            Response res = new Response("", true, "An unknown error occurred!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        appointmentServiceImpl.saveAppointment(appointmentModel);
        Map<String, Object> data = new HashMap<>();
        data.put("type", "appointment");
        data.put("id", appointmentModel.getId().toString());
        data.put("action", "update");
        data.put("data", appointmentModel);
        data.put("message",
                "Your appointment with " + doctorModel.get().getDoctorName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is updated!");
        NotificationModel notificationModel = new NotificationModel("patient", appointmentModel.getPatientId(),
                data.get("message").toString(), "Appointment Updated", "", "warning", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
        data.put("message",
                "Your appointment with " + patientModel.get().getPatientName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is updated!");
        notificationModel = new NotificationModel("doctor", appointmentModel.getDoctorId(),
                data.get("message").toString(), "Appointment Updated", "", "warning", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        Response res = new Response(appointmentModel, false, "Appointment updated Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAppointment(@RequestBody AppointmentModel appointmentModel) throws Exception {
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(appointmentModel.getDoctorId());
        Optional<PatientModel> patientModel = patientServiceImpl.getPatientById(appointmentModel.getPatientId());
        if (doctorModel.isEmpty() || patientModel.isEmpty()) {
            Response res = new Response("", true, "An unknown error occurred!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        appointmentServiceImpl.saveAppointment(appointmentModel);
        Map<String, Object> data = new HashMap<>();
        data.put("type", "appointment");
        data.put("id", appointmentModel.getId().toString());
        data.put("action", "delete");
        data.put("data", appointmentModel);
        data.put("message",
                "Your appointment with " + doctorModel.get().getDoctorName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is deleted!");
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
        NotificationModel notificationModel = new NotificationModel("patient", appointmentModel.getPatientId(),
                data.get("message").toString(), "Appointment Cancelled", "", "danger", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        data.put("message",
                "Your appointment with " + patientModel.get().getPatientName() + " on "
                        + appointmentModel.getStartTime().toLocalDate() + ", "
                        + appointmentModel.getStartTime().toLocalTime() + " is deleted!");
        notificationModel = new NotificationModel("doctor", appointmentModel.getDoctorId(),
                data.get("message").toString(), "Appointment Cancelled", "", "danger", LocalDateTime.now());
        notificationServiceImpl.saveNotification(notificationModel);
        simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        Response res = new Response(appointmentModel, false, "Appointment deleted Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/fetchAppointmentsByDoctor")
    public ResponseEntity fetchAppointmentsByDoctor(@RequestBody AppointmentModel appointmentModel) {
        List<AppointmentModel> appointmentModelList = appointmentServiceImpl
                .fetchAppointmentsByDoctor(appointmentModel.getDoctorId());
        Response res = new Response(appointmentModelList, false, "Appointments fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/fetchDoctorNamesByAppointments")
    public ResponseEntity fetchDoctorNamesByAppointments(@RequestBody PatientModel patientModel) {
        List<DoctorModel> doctorModelList = appointmentServiceImpl
                .fetchDoctorNamesByPatientsAppointment(patientModel.getId());
        Response res = new Response(doctorModelList, false, "Doctors fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/fetchAppointmentsByPatient")
    public ResponseEntity fetchAppointmentsByPatient(@RequestBody AppointmentModel appointmentModel) {
        List<AppointmentModel> appointmentModelList = appointmentServiceImpl
                .fetchAppointmentsByPatient(appointmentModel.getPatientId());
        Response res = new Response(appointmentModelList, false, "Appointments fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/fetchAppointmentsByPatientAfterDate")
    public ResponseEntity fetchAppointmentsByPatientAfterDate(@RequestBody AppointmentModel appointmentModel) {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusDays(1);
        List<AppointmentModel> appointmentModelList = appointmentServiceImpl
                .fetchAppointmentsByPatientAfterDate(appointmentModel.getPatientId(), dateTime);
        Response res = new Response(appointmentModelList, false, "Appointments fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
