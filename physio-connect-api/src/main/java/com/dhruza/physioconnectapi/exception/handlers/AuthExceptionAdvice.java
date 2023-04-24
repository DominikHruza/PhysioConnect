package com.dhruza.physioconnectapi.exception.handlers;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dhruza.physioconnectapi.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class AuthExceptionAdvice {

    @ExceptionHandler({SecurityException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse onSecurityException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return errorResponse;
    }

    @ExceptionHandler({ SignatureVerificationException.class, JWTDecodeException.class, TokenExpiredException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse onSignatureVerificationException(Exception e) {
        log.info(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Invalid Token");
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return errorResponse;
    }
}
