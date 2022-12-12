package com.javacadets.rohan.exceptions;

import com.javacadets.rohan.entities.Student;

public class StudentNotEnrolledException extends Exception{
    public StudentNotEnrolledException (Student student) {
        super("Student: "+student.getEmail()+" is not enrolled in this class");
    }
}
