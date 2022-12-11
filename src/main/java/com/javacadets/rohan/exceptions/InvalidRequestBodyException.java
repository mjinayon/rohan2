package com.javacadets.rohan.exceptions;

import java.util.Map;

public class InvalidRequestBodyException extends Exception{
    public InvalidRequestBodyException(Map<String, ? extends Object> request) {
        super(String.format("Invalid request body: %s", request.toString()));
    }
}
