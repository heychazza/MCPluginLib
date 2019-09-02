package dev.chapi.api.exception;

public class InvalidInventoryException extends Exception {
    public InvalidInventoryException() {
        super("You need to specify a valid inventory size or type.");
    }
}
