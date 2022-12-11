package com.javacadets.rohan.entities;

import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"project_id", "user_id"})})
public class ProjectRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectRecordId;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName="userId")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Project project;

    public ProjectRecord() {}

    public ProjectRecord(Student student, Project project, int score) {
        this.student = student;
        this.project = project;
        this.score = score;
    }

    public long getProjectRecordId() {
        return projectRecordId;
    }

    public int getScore() {
        return score;
    }

    public Student getStudent() {
        return student;
    }

    public Project getProject() {
        return project;
    }
}
