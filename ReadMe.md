# Medinteract

## External Dependencies

- Java.11
- Maven.4.0.0
- Apache-2.0
- Ability to connect to the Dalhousie University server

## Build Documentation
- Install & Verify Java 11 Version
```
 sudo apt-get update
```

```
 sudo apt-get install openjdk-11-jdk
```

```
 java -version
```

- Install Maven
```
 sudo apt-get install maven
```
```
 mvn -version
```

- Install Apache for Frontend
```
 sudo apt-get install apache2
```

- Maven Build
```
 mvn clean package
```

- Maven Test
```
 mvn verify
```

- Run Jar File
```
 java -jar <final_image.jar>
```


## User Scenarios

- **Authentication Module (Sign-in/up)**

Registration:
New user can register either as a doctor or a patient using their credentials and information. Doctors need to provide the qualification documents on top of all other personal information.

Registration as a doctor:

![alt text](/screenshots/doctor-register.png)

![alt text](/screenshots/doctor-register-fill.png)

Registration as a patient:

![alt text](/screenshots/patient-register.png)

![alt text](/screenshots/patient-register-fill.png)

After registration, users, either doctors or patients can log into their account using their email address and password. However, doctors need to be verified by the admin before being able to log into their account.

Log in page (doctors/patients)

![alt text](/screenshots/login.png)

![alt text](/screenshots/patient-login.png)

- **Dashboard Module**

In this page, patient can see list of doctors in all locations and qualification. Patient can filter doctors based on doctor's name, location (province and city), and qualification. Also, All doctors have a *Feedback* and *Book* buttons that patients can leave a comment and rate their doctors and book an appointment with them, respectively.

![alt text](/screenshots/dashboard-main.png)

Filter based on the name: For example, if you search for the word *John*:

![alt text](/screenshots/dashboard-province-name.png)

`Filter based on province: For example if you filter it to show the doctors for Manitoba (MB):

![alt text](/screenshots/dashboard-provinceMB.png)

Filter based on province and city:

![alt text](/screenshots/dashboard-province-city.png)

![alt text](/screenshots/dashboard-province-city-1.png)

- **Profile Module**

In this page, patients and doctors can see and edit their information. By clicking the "Update Profile" button, user will be able to change their photo and edit authorized fields and then save the changes.

Patient profile:

![alt text](/screenshots/profile-patient.png)

Patients can click on *Update Profile* button and update the authorized fields of information. They cannot update the email address.

![alt text](/screenshots/profile-patient-update-1.png)

![alt text](/screenshots/profile-patient-update-2.png)


Doctor profile:

![alt text](/screenshots/profile-doctor.png)

Doctors can update their profile by clicking on *Update Profile* button. They cannot change email address.

![alt text](/screenshots/profile-doctor-1.png)

![alt text](/screenshots/profile-doctor-2.png)

- **Scheduling and Calendar Module**

Using this module, patients can book a time slot using the calendar. When they book an appointment, they receive a notification. Then booking is visible in their *Dashboard*

![alt text](/screenshots/scheduling.png)

- **ChatBot Module**

Using this module, both patients and doctors can have a chat with MEDINTERACT bot to ask some common questions.

![alt text](/screenshots/chatbot-view-1.png)

There are two options for user to ask for the chatbot. For example, if they click on the *Emergency Contact*, they will have:

![alt text](/screenshots/chatbot-view-emergency.png)

In the other case, if user click on the *My Appointment*, they have a calendar to book an appointment.

![alt text](/screenshots/chatbot-view-bookAppointment.png)


- **Notifications Module**

All users (Doctors and Patients) will receive the notification after each activity.

![alt text](/screenshots/Notifications.png)

![alt text](/screenshots/notification-booking.png)

- **Articles Module**

Using this module, doctors can create, update and delete articles. They are able to upload pictures and post rich HTML text. Patients only have the read option. They cannot update or delete articles.

Patient view:

![alt text](/screenshots/article-2.png)

Doctor view, (create, edit, delete article)

![alt text](/screenshots/article-1.png)

- **Prescription Module**

Using this module, doctors can prescribe medications to patients by specifying the details like medication name, dosage, and how often patient has to take them.

Doctor view:

![alt text](/screenshots/prescription.png)

Patient view:

![alt text](/screenshots/prescription-patient.png)

![alt text](/screenshots/prescription-patient-1.png)

![alt text](/screenshots/prescription-patient-2.png)

- **Admin: Doctor Verification Module**

In this module, the admin has the authority to confirm or reject a doctor's qualification based on their document. There are two buttons that Admin can verify or block the doctor's application

![alt text](/screenshots/admin-verify-doctor.png)

- **Feedback and Rating Module**

Using this module, patients can rate their doctors and also leave a comment for them.

![alt text](/screenshots/feedback.png)


## Smell Analysis Summary

- Designite Report Files Attached

## Member Contribution

- Excel File Attached

## Client Team Feedback

- Excel File Attached

## Presentation

- PPT File Attached
