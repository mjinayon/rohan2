package com.javacadets.rohan.helpers;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorHandler {
    public static Map<String, Object> err (Exception e) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", HttpStatus.BAD_REQUEST.value());
        error.put("message", e.getMessage());

        Map<String, Object> returnError = new HashMap<>();
        returnError.put("error",error);
        return returnError;
    }
}
