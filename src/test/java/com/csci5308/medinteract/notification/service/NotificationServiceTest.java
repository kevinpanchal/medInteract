package com.csci5308.medinteract.notification.service;

import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = NotificationService.class)
public class NotificationServiceTest {

    @MockBean
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Test
    public void saveNotificationTest() {
        NotificationModel notificationModel = new NotificationModel(
                "Doctor", 123L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        );
        when(notificationRepository.save(any())).thenReturn(notificationModel);
        NotificationModel savedNotificationModel = notificationService.saveNotification(notificationModel);
        Assertions.assertEquals(notificationModel, savedNotificationModel);
    }

    @Test
    public void fetchAllTest() {
        List<NotificationModel> notificationModelList = new ArrayList<>();
        notificationModelList.add(new NotificationModel(
                "Doctor", 123L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        ));
        notificationModelList.add(new NotificationModel(
                "Doctor", 456L, "New message",
                "Notification", "http://localhost", "success",
                LocalDateTime.now()
        ));
        when(notificationRepository.findAllByUserIdAndAndUserTypeOrderByNotificationDateTimeDesc(any(), any())).thenReturn(notificationModelList);
        List<NotificationModel> fetchedNotificationModelList = notificationService.fetchAll(123L, "Doctor");
        Assertions.assertEquals(notificationModelList, fetchedNotificationModelList);
    }
}

