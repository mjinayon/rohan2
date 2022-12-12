package com.javacadets.rohan.entities;

import com.javacadets.rohan.exceptions.ClassNotYetStartedException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"class_id", "date", "student_id"})})
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attendanceRecordId;
    private LocalDate date;
    private boolean isPresent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "classId")
    private Classs classs;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "userId")
    private Student student;

    public AttendanceRecord() {}
    public AttendanceRecord(Classs classs, Student student) throws ClassNotYetStartedException {
        if (!(classs.getStartDate().before(new Date()) && classs.getEndDate().after(new Date()))) {
            throw new ClassNotYetStartedException(classs.getCourse().getCode(), classs.getBatch());
        }
        this.date = date.now();
        this.classs = classs;
        this.student = student;
        this.isPresent = false;
    }

    public long getAttendanceRecordId() {
        return attendanceRecordId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Classs getClasss() {
        return classs;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public AttendanceRecord present() {
        this.isPresent = true;
        return this;
    }

    public AttendanceRecord absent() {
        this.isPresent = false;
        return this;
    }
}
