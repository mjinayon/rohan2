package com.javacadets.rohan.exceptions;

public class ExerciseNotFoundException extends Exception{
    public ExerciseNotFoundException(long id) {
        super(String.format("Exercise id '%d' not found!", id));
    }
}
