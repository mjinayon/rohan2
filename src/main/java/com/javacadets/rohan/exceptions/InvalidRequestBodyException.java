package com.javacadets.rohan.exceptions;

import java.util.Map;

public class InvalidRequestBodyException extends Exception{
    public InvalidRequestBodyException(Map<String, String> request) {
        super(String.format("Invalid request body: %s", request.toString()));
    }
}
