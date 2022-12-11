package com.javacadets.rohan.exceptions;

public class QuizRecordNotEmptyException extends Exception{
    public QuizRecordNotEmptyException(long quizId) {
        super(String.format("Quiz id '%d' has records", quizId));
    }
}
