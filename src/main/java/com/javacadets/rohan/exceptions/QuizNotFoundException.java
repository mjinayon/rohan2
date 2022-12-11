package com.javacadets.rohan.exceptions;

public class QuizNotFoundException extends Exception{
    public QuizNotFoundException(long id) {
        super(String.format("Quiz id '%d' not found!", id));
    }
}
