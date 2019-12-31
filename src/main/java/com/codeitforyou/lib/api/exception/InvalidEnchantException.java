package com.codeitforyou.lib.api.exception;

public class InvalidEnchantException extends Exception {
    public InvalidEnchantException(String errorMessage) {
        super(errorMessage + " is an unknown enchant.");
    }
}
