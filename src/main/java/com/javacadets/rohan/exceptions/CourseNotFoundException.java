package com.javacadets.rohan.exceptions;

public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(String code) {
        super(String.format("Course '%s' not found!", code));
    }
}
