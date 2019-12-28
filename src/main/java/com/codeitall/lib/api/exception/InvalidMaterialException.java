package com.codeitall.lib.api.exception;

public class InvalidMaterialException extends Exception {
    public InvalidMaterialException(String errorMessage) {
        super(errorMessage + " is an unknown material.");
    }
}
