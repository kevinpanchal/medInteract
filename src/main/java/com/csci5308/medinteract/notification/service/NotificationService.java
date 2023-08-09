package com.csci5308.medinteract.notification.service;

import com.csci5308.medinteract.notification.model.NotificationModel;

import java.util.List;

public interface NotificationService {
    NotificationModel saveNotification(NotificationModel notificationModel);
    List<NotificationModel> fetchAll(Long userId, String userType);

}
