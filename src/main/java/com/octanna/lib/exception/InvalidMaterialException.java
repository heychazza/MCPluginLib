package com.octanna.lib.exception;

public class InvalidMaterialException extends Exception {
    public InvalidMaterialException(String errorMessage) {
        super(errorMessage + " is an unknown material.");
    }
}
