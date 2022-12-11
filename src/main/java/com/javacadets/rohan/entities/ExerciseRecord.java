package com.javacadets.rohan.entities;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"exercise_id", "user_id"})})
public class ExerciseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long exerciseRecordId;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName="userId")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id", referencedColumnName = "exerciseId")
    private Exercise exercise;

    public ExerciseRecord() {}

    public ExerciseRecord(Student student, Exercise exercise, int score) {
        this.student = student;
        this.exercise = exercise;
        this.score = score;
    }

    public long getExerciseRecordId() { return exerciseRecordId; }

    public int getScore() { return score; }

    public Student getStudent() { return student; }

    public Exercise getExercise() { return exercise; }
}
