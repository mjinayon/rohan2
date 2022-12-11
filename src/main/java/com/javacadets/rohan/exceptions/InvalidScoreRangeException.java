package com.javacadets.rohan.exceptions;

public class InvalidScoreRangeException extends Exception{
    public InvalidScoreRangeException(int min, int max, int given) {
        super(String.format("Score not in range %d-%d, given %d", min, max, given));
    }
}
