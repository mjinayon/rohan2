package com.javacadets.rohan.exceptions;

import com.javacadets.rohan.entities.Classs;

public class StudentAlreadyEnrolledException extends Exception {
    public StudentAlreadyEnrolledException(Classs classs) {
        super(String.format("Student already enrolled in this course: %s",classs.getCourse().getCode()));
    }
}
