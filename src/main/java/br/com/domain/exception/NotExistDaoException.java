package br.com.domain.exception;

public class NotExistDaoException extends RuntimeException {

    public NotExistDaoException(String message) {
        super(message);
    }
}
