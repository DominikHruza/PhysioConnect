package com.dhruza.physioconnectapi.exception;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message) {
        super(message);
    }

    public UniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueConstraintException(Throwable cause) {
        super(cause);
    }

    public UniqueConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
