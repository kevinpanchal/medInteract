package com.csci5308.medinteract.Scheduler;

import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.service.AppointmentService;
import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.service.NotificationService;
import com.csci5308.medinteract.patient.model.PatientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Scheduler {
    private AppointmentService appointmentServiceImpl;
    private final NotificationService notificationServiceImpl;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public Scheduler(AppointmentService appointmentServiceImpl, NotificationService notificationServiceImpl) {
        this.appointmentServiceImpl = appointmentServiceImpl;
        this.notificationServiceImpl = notificationServiceImpl;
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void testScheduler() {
//        List<Object> appointmentModelList = appointmentServiceImpl.fetchAppointmentsHourly();
//        for(int i = 0; i < appointmentModelList.size(); i++) {
//            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
//            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
//            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
//            Map<String, Object> data = new HashMap<>();
//            data.put("type", "reminder");
//            data.put("id", appointmentModel.getId().toString());
//            data.put("action", "reminder");
//            data.put("data", appointmentModelList.get(i));
//            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for within one hour!");
//            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Hourly Reminder", "", "primary", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
//            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for within one hour!");
//            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Hourly Reminder", "", "primary", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
//        }
//
//        appointmentModelList = appointmentServiceImpl.fetchAppointmentsDaily();
//        for(int i = 0; i < appointmentModelList.size(); i++) {
//            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
//            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
//            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
//            Map<String, Object> data = new HashMap<>();
//            data.put("type", "reminder");
//            data.put("id", appointmentModel.getId().toString());
//            data.put("action", "reminder");
//            data.put("data", appointmentModelList.get(i));
//            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for today!");
//            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Daily Reminder", "", "info", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
//            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for today!");
//            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Daily Reminder", "", "info", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
//        }
//
//        appointmentModelList = appointmentServiceImpl.fetchAppointmentsWithinThreeDays();
//        for(int i = 0; i < appointmentModelList.size(); i++) {
//            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
//            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
//            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
//            Map<String, Object> data = new HashMap<>();
//            data.put("type", "reminder");
//            data.put("id", appointmentModel.getId().toString());
//            data.put("action", "reminder");
//            data.put("data", appointmentModelList.get(i));
//            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after 3 days!");
//            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Three Days Reminder", "", "dark", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
//            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after 3 days!");
//            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Three Days Reminder", "", "dark", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
//        }
//
//        appointmentModelList = appointmentServiceImpl.fetchAppointmentsWeekly();
//        for(int i = 0; i < appointmentModelList.size(); i++) {
//            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
//            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
//            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
//            Map<String, Object> data = new HashMap<>();
//            data.put("type", "reminder");
//            data.put("id", appointmentModel.getId().toString());
//            data.put("action", "reminder");
//            data.put("data", appointmentModelList.get(i));
//            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after a week!");
//            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Weekly Reminder", "", "light", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
//            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after a week");
//            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Weekly Reminder", "", "light", LocalDateTime.now());
//            notificationServiceImpl.saveNotification(notificationModel);
//            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
//        }
//    }

    @Scheduled(cron = "@hourly")
    public void hourlyScheduler() {
        List<Object> appointmentModelList = appointmentServiceImpl.fetchAppointmentsHourly();
        for(int i = 0; i < appointmentModelList.size(); i++) {
            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();
            data.put("type", "reminder");
            data.put("id", appointmentModel.getId().toString());
            data.put("action", "reminder");
            data.put("data", appointmentModelList.get(i));
            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for within one hour!");
            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Hourly Reminder", "", "primary", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for within one hour!");
            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Hourly Reminder", "", "primary", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        }
    }

    @Scheduled(cron = "@daily")
    public void dailyScheduler() {
        //checks for daily, 3days and week scheduler
        List<Object> appointmentModelList = appointmentServiceImpl.fetchAppointmentsDaily();
        for(int i = 0; i < appointmentModelList.size(); i++) {
            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();
            data.put("type", "reminder");
            data.put("id", appointmentModel.getId().toString());
            data.put("action", "reminder");
            data.put("data", appointmentModelList.get(i));
            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for today!");
            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Daily Reminder", "", "info", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for today!");
            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Daily Reminder", "", "info", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        }

        appointmentModelList = appointmentServiceImpl.fetchAppointmentsWithinThreeDays();
        for(int i = 0; i < appointmentModelList.size(); i++) {
            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();
            data.put("type", "reminder");
            data.put("id", appointmentModel.getId().toString());
            data.put("action", "reminder");
            data.put("data", appointmentModelList.get(i));
            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after 3 days!");
            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Three Days Reminder", "", "dark", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after 3 days!");
            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Three Days Reminder", "", "dark", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        }

        appointmentModelList = appointmentServiceImpl.fetchAppointmentsWeekly();
        for(int i = 0; i < appointmentModelList.size(); i++) {
            AppointmentModel appointmentModel = (AppointmentModel) (((Object[]) appointmentModelList.get(i))[0]);
            PatientModel patientModel = (PatientModel) (((Object[]) appointmentModelList.get(i))[1]);
            DoctorModel doctorModel = (DoctorModel) (((Object[]) appointmentModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();
            data.put("type", "reminder");
            data.put("id", appointmentModel.getId().toString());
            data.put("action", "reminder");
            data.put("data", appointmentModelList.get(i));
            data.put("message", "Your upcoming appointment with " + doctorModel.getDoctorName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after a week!");
            NotificationModel notificationModel = new NotificationModel("patient", patientModel.getId(), data.get("message").toString(), "Weekly Reminder", "", "light", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getPatientId().toString(), "/patient", data);
            data.put("message", "Your upcoming appointment with " + patientModel.getPatientName() + " on " + appointmentModel.getStartTime().toLocalDate() + ", " + appointmentModel.getStartTime().toLocalTime() + " is scheduled for after a week");
            notificationModel = new NotificationModel("doctor", doctorModel.getId(), data.get("message").toString(), "Weekly Reminder", "", "light", LocalDateTime.now());
            notificationServiceImpl.saveNotification(notificationModel);
            simpMessagingTemplate.convertAndSendToUser(appointmentModel.getDoctorId().toString(), "/doctor", data);
        }

    }
}
