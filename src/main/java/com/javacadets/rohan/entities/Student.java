package com.javacadets.rohan.entities;

import com.javacadets.rohan.constants.RohanRoles;
import com.javacadets.rohan.constants.RohanStatus;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<AttendanceRecord> attendanceRecord;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<ExerciseRecord> exerciseRecords;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<ProjectRecord> projectRecords;

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

    }

    public Set<Classs> getClasses() {
        return classes;
    }

    public Set<QuizRecord> getQuizRecords() {
        return quizRecords;
    }

    public Set<AttendanceRecord> getAttendanceRecord() {
        return attendanceRecord;
    }

    public Set<ExerciseRecord> getExerciseRecords() {
        return exerciseRecords;
    }

    public Set<ProjectRecord> getProjectRecords() {
        return projectRecords;
    }
}
