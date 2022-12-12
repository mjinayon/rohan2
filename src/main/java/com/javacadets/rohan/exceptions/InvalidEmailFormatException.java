package com.javacadets.rohan.exceptions;

public class InvalidEmailFormatException extends Exception{
    public InvalidEmailFormatException(String email) {
        super(String.format("Invalid email format '%s'", email));
    }
}
