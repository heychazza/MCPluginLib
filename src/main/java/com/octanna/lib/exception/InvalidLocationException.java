package com.octanna.lib.exception;

public class InvalidLocationException extends Exception {
    public InvalidLocationException() {
        super("That's an unknown location.");
    }
}
