package com.csci5308.medinteract.notification.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
@Table(name="notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_generator")
    @SequenceGenerator(name="notification_generator", sequenceName = "notification_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @NotNull
    private String userType;

    @NotBlank
    @NotNull
    private Long userId;

    @NotBlank
    @NotNull
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String url;

    @NotBlank
    @NotNull
    private String notificationType; //danger, warning, success

    @NotBlank
    @NotNull
    private LocalDateTime notificationDateTime;

    public NotificationModel(String userType, Long userId, String message, String title, String url, String notificationType, LocalDateTime notificationDateTime) {
        this.userType = userType;
        this.userId = userId;
        this.message = message;
        this.title = title;
        this.url = url;
        this.notificationType = notificationType;
        this.notificationDateTime = notificationDateTime;
    }

    public NotificationModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }
}
