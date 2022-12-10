package com.javacadets.rohan.exceptions;

public class InvalidGradingPercentageException extends Exception{
    public InvalidGradingPercentageException(double sum) {
        super(String.format("Invalid grading percentage 'sum = %    f'", sum));
    }
}
