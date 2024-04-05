package com.project.soulsoundapp.model;

import androidx.annotation.NonNull;

import java.util.UUID;

public class User {
    private String email;
    private String fullName;

    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User(String userId, String email, String fullName, String password) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

