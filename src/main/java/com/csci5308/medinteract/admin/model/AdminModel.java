package com.csci5308.medinteract.admin.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



    @Entity
    @Table(name = "admin")
    public class AdminModel {
        @Id
        @NotBlank
        @NotNull
        private String adminEmail;

        @NotNull
        private  boolean isActive;

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        @NotBlank
        @NotNull


        public AdminModel() {
        }



        public AdminModel(String adminUserName, String adminPassword) {
            this.adminEmail = adminUserName;
            this.adminPassword = adminPassword;
        }

        private String adminPassword;

        public String getAdminPassword() {
            return adminPassword;
        }

        public void setAdminPassword(String adminPassword) {
            this.adminPassword = adminPassword;
        }
        public @NotBlank @NotNull String getAdminEmail() {
            return adminEmail;
        }

        public void setAdminEmail(String adminEmail) {
            this.adminEmail = adminEmail;
        }

    }

