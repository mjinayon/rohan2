package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.Entity;

@Entity
public class Student extends User {
    private String role;
    private String status;

    public Student() {}

    public Student(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
        this.role = RohanRoles.STUDENT;
        this.status = RohanStatus.ACTIVATED;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.getUserId();
    }
}