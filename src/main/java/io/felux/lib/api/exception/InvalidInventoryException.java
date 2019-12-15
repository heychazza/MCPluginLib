package io.felux.lib.api.exception;

public class InvalidInventoryException extends Exception {
    public InvalidInventoryException(String errorMessage) {
        super(errorMessage);
    }
}
