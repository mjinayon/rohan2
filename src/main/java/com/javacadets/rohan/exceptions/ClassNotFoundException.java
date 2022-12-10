package com.javacadets.rohan.exceptions;

public class ClassNotFoundException extends Exception {

    public ClassNotFoundException(String code,int batch) {
        super(String.format("Class with course code '%s' and batch number '%d' not found!", code, batch));
    }
}
