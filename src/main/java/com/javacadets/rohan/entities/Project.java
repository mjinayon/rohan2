package com.javacadets.rohan.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity (name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;
    private String title;
    private int maxScore;
    private int minScore;


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="class_id", referencedColumnName = "classId")
    Classs classs;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Set<ProjectRecord> projectRecords;

    public Project() {}

    public Project(Classs classs, String title, int maxScore, int minScore) {
        this.title = title;
        this.maxScore = maxScore;
        this.minScore = minScore;

        this.classs = classs;
    }

    public long getProjectId() {
        return projectId;
    }


    public int getMaxScore() {
        return maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public Classs getClasss() {
        return classs;
    }

}
