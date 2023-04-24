package com.dhruza.physioconnectapi.exception;

public class StorageFileException extends RuntimeException{
    public StorageFileException() {
    }

    public StorageFileException(String message) {
        super(message);
    }

    public StorageFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageFileException(Throwable cause) {
        super(cause);
    }
}
