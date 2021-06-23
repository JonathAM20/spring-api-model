package br.com.domain.exception;

public class ViolationConstraintException extends RuntimeException {

    public ViolationConstraintException(String message) {
        super(message);
    }
}
