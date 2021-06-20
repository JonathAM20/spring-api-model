package br.com.domain.exception;

public class IdInvalidServiceException extends RuntimeException {

    public IdInvalidServiceException(String message) {
        super(message);
    }
}
