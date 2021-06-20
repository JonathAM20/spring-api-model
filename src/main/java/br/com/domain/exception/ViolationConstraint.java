package br.com.domain.exception;

public class ViolationConstraint extends RuntimeException {

    public ViolationConstraint(String message) {
        super(message);
    }
}
