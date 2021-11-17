package com.octanna.lib.exception;

public class InvalidFlagException extends Exception {
    public InvalidFlagException(String errorMessage) {
        super(errorMessage + " is an unknown flag.");
    }
}
