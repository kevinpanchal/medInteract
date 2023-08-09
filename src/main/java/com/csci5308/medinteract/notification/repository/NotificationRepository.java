package com.csci5308.medinteract.notification.repository;

import com.csci5308.medinteract.notification.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    List<NotificationModel> findAllByUserIdAndAndUserTypeOrderByNotificationDateTimeDesc(Long userId, String userType);
}
