package com.csci5308.medinteract.notification.controller;

import com.csci5308.medinteract.notification.model.NotificationModel;
import com.csci5308.medinteract.notification.service.NotificationService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationServiceImpl;

    @Autowired
    public NotificationController(NotificationService notificationServiceImpl) {
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @PostMapping("/fetchAll")
    public ResponseEntity fetchAll(@RequestBody NotificationModel notificationModel)
    {
        List<NotificationModel> notificationModelList= notificationServiceImpl.fetchAll(notificationModel.getUserId(), notificationModel.getUserType());
        Response res = new Response(notificationModelList, false, "All notifications fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
