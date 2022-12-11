package com.javacadets.rohan.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"quiz_id", "user_id"})})
public class QuizRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizRecordId;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName="userId")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName="quizId")
    private Quiz quiz;

    public QuizRecord() {}

    public QuizRecord(Student student, Quiz quiz, int score) {
        this.student = student;
        this.quiz = quiz;
        this.score = score;
    }

    public long getQuizRecordId() {
        return quizRecordId;
    }

    public int getScore() {
        return score;
    }

    public Student getStudent() {
        return student;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
