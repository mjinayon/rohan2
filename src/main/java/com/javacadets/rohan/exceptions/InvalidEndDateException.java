package com.javacadets.rohan.exceptions;

import java.util.Date;

public class InvalidEndDateException extends Exception{
    public InvalidEndDateException(Date start, Date end) {
        super(String.format("Invalid end date: start=%s, end=%s", start.toString(), end.toString()));
    }
}
