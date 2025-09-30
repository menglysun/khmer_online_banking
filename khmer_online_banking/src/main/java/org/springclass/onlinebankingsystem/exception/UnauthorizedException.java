package org.springclass.onlinebankingsystem.exception;

public class UnauthorizedException extends RuntimeException {
    private final String error;
    private final String message;

    public UnauthorizedException(final String error, final String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() { return error; }

    @Override
    public String getMessage() { return message; }
}
