package com.octanna.lib.exception;

public class InvalidEnchantException extends Exception {
    public InvalidEnchantException(String errorMessage) {
        super(errorMessage + " is an unknown enchant.");
    }
}
