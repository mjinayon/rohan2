package com.javacadets.rohan.exceptions;

public class ClassNotYetStartedException extends Exception{
    public ClassNotYetStartedException(String code, int batch) {
        super(String.format("Class code '%s' batch '%d' not yet started!", code, batch));
    }
}
