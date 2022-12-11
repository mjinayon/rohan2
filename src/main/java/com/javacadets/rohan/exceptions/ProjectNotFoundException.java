package com.javacadets.rohan.exceptions;

import com.javacadets.rohan.entities.Classs;

public class ProjectNotFoundException extends Exception{

    public ProjectNotFoundException(Classs classs) {
        super(String.format("Project id '%d' not found!", classs));
    }
}
