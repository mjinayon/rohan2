package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Student extends User {
    private String role;
    private String status;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Classs> classes;

    public Student() {}

    public Student(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
        this.role = RohanRoles.STUDENT;
        this.status = RohanStatus.ACTIVATED;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.getUserId();
    }
}
