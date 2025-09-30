package org.springclass.onlinebankingsystem.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
public class CustomException extends RuntimeException {
    @Getter
    private int status;
    private String message;

    public CustomException(final int statusCode, final String message) {
        super(message);
        this.status = statusCode;
        this.message = message;
    }

    @Override
    public String getMessage() { return message; }
}
