package com.javacadets.rohan.exceptions;

import com.javacadets.rohan.entities.Course;

public class DeactivatedCourseException extends Exception{

    public DeactivatedCourseException(Course course) {
        super(String.format("Course '%s' is already deactivated.",course.getCode()));
    }
}
