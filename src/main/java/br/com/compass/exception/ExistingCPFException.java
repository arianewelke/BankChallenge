package br.com.compass.exception;

public class ExistingCPFException extends RuntimeException {
    public ExistingCPFException(String message) {
        super(message);
    }
}
