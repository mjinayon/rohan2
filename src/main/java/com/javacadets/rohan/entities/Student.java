package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Student extends User {
    private final String role  = RohanRoles.STUDENT;
    private String status;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Classs> classes;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<QuizRecord> quizRecords;

    public Student() {}

    public Student(String email, String firstName, String lastName) {
        super(email, firstName, lastName);
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
