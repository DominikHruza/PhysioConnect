package com.dhruza.physioconnectapi.exception.handlers;

import com.dhruza.physioconnectapi.dto.ErrorResponse;
import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.exception.StorageFileException;
import com.dhruza.physioconnectapi.exception.UniqueConstraintException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Set;


@ControllerAdvice
public class DefaultExceptionsControllerAdvice {

    @ExceptionHandler({
            UniqueConstraintException.class,
            DataNotFoundException.class,
            DataIntegrityViolationException.class,
            StorageFileException.class,

    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse onException(
            Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return errorResponse;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Set<String> handleValidationExceptions(ConstraintViolationException ex) {
        Set<String> errors = new HashSet<>();
        ex.getConstraintViolations().forEach(error ->
                errors.add(error.getMessage()));
        return errors;
    }
}
