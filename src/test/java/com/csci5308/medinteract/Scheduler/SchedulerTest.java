package com.csci5308.medinteract.Scheduler;

import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import com.csci5308.medinteract.appointment.service.AppointmentService;
import com.csci5308.medinteract.appointment.service.AppointmentServiceImpl;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.repository.DoctorRepository;
import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.service.NotificationService;
import com.csci5308.medinteract.notification.service.NotificationServiceImpl;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {Scheduler.class})
@ExtendWith(SpringExtension.class)
class SchedulerTest {
    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private Scheduler scheduler;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private NotificationServiceImpl notificationServiceImpl;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceImpl;

    private AppointmentModel appointmentModel;
    private PatientModel patientModel;
    private DoctorModel doctorModel;

    private NotificationModel notificationModel;
    private Object[] appointmentData;
    private List<Object> appointmentDataList;

    public SchedulerTest() {
        appointmentModel = new AppointmentModel();
        appointmentModel.setId(1L);
        appointmentModel.setStartTime(LocalDateTime.now());
        appointmentModel.setPatientId(1L);
        appointmentModel.setDoctorId(1L);

        patientModel = new PatientModel();
        patientModel.setId(1L);
        patientModel.setPatientName("Rose");

        doctorModel = new DoctorModel();
        doctorModel.setId(1L);
        doctorModel.setDoctorName("Hannah");

        appointmentData = new Object[] {appointmentModel, patientModel, doctorModel};
        appointmentDataList = new ArrayList<>();
        appointmentDataList.add(appointmentData);

        notificationModel = new NotificationModel();

        notificationModel.setUserId(1L);
    }

    @Test
    void testHourlyScheduler() {

        Mockito.when(notificationService.saveNotification(Mockito.any(NotificationModel.class))).thenReturn(notificationModel);

        when(appointmentService.fetchAppointmentsHourly()).thenReturn(appointmentDataList);
        scheduler.hourlyScheduler();
        verify(appointmentService).fetchAppointmentsHourly();
    }

    @Test
    void testDailyScheduler() {
        Mockito.when(notificationService.saveNotification(Mockito.any(NotificationModel.class))).thenReturn(notificationModel);
        when(appointmentService.fetchAppointmentsDaily()).thenReturn(appointmentDataList);
        when(appointmentService.fetchAppointmentsWithinThreeDays()).thenReturn(appointmentDataList);
        when(appointmentService.fetchAppointmentsWeekly()).thenReturn(appointmentDataList);
        scheduler.dailyScheduler();
        verify(appointmentService).fetchAppointmentsDaily();
    }
}

