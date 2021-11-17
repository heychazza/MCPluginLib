package com.octanna.lib.exception;

public class InvalidInventoryException extends Exception {
    public InvalidInventoryException(String errorMessage) {
        super(errorMessage);
    }
}
