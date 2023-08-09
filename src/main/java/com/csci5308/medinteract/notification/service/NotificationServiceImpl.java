package com.csci5308.medinteract.notification.service;

import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationModel saveNotification(NotificationModel notificationModel) {
        return notificationRepository.save(notificationModel);
    }

    @Override
    public List<NotificationModel> fetchAll(Long userId, String userType) {
        return notificationRepository.findAllByUserIdAndAndUserTypeOrderByNotificationDateTimeDesc(userId, userType);
    }
}
