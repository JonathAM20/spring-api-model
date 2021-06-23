package br.com.domain.exception;

public class NotExistException extends RuntimeException {

    public NotExistException(String message) {
        super(message);
    }
}
