package com.javacadets.rohan.exceptions;

public class InvalidRoleException extends Exception{
    public InvalidRoleException(String role) {
        super(String.format("Invalid role '%s'!", role));
    }
}
