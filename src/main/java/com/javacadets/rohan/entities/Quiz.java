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

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz quiz)) return false;
        return quizId == quiz.quizId && classs.equals(quiz.classs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, classs);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", title='" + title + '\'' +
                ", maxScore=" + maxScore +
                ", minScore=" + minScore +
                ", classs=" + classs +
                '}';
    }
}
