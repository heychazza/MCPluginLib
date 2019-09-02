package dev.chapi.api.exception;

public class InvalidLocationException extends Exception {
    public InvalidLocationException() {
        super("That's an unknown location.");
    }
}
