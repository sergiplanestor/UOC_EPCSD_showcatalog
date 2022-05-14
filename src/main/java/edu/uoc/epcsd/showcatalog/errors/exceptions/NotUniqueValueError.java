package edu.uoc.epcsd.showcatalog.errors.exceptions;

public class NotUniqueValueError extends Exception {
    public NotUniqueValueError() {
        this("The value provided is invalid. It must be unique and it already exist.");
    }

    public NotUniqueValueError(String message) {
        super(message);
    }
}
