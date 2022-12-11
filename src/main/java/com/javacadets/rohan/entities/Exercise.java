package com.javacadets.rohan.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long exerciseId;
    private String title;
    private int maxScore;
    private int minScore;
    private Date date;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="class_id", referencedColumnName = "classId")
    Classs classs;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private Set<ExerciseRecord> exerciseRecords;

    public Set<ExerciseRecord> getExerciseRecords() {
        return exerciseRecords;
    }

    public Exercise() {}

    public Exercise(String title, int maxScore, int minScore, Date date, Classs classs) {
        this.title = title;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.date = date;
        this.classs = classs;
        this.isDeleted = false;
    }

    public long getExerciseId() {
        return exerciseId;
    }


    public String getTitle() {
        return title;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public Date getDate() {
        return date;
    }
    public Classs getClasss() {
        return classs;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Exercise delete() {
        this.isDeleted = true;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise exercise)) return false;
        return exerciseId == exercise.exerciseId && classs.equals(exercise.classs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseId, classs);
    }
}
