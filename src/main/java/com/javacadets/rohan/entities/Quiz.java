package com.javacadets.rohan.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizId;
    private String title;
    private int maxScore;
    private int minScore;
    private Date date;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="class_id", referencedColumnName = "classId")
    private Classs classs;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<QuizRecord> quizRecords;

    public Quiz() {}

    public Quiz(String title, int minScore, int maxScore, Date date, Classs classs) {
        this.title = title;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.classs = classs;
        this.date = date;
        this.isDeleted = false;
    }

    public long getQuizId() {
        return quizId;
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

    public Classs getClasss() {
        return classs;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public Quiz delete() {
        this.isDeleted = true;
        return this;
    }

    public Set<QuizRecord> getQuizRecords() {
        return quizRecords;
    }

}
