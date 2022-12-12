package com.javacadets.rohan.exceptions;

public class DeactivatedUserException extends  Exception {
    public DeactivatedUserException(String email) {
        super(String.format("User with email '%s' not found or is probably deactivated!", email));
    }
}
