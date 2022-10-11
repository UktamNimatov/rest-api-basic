package com.epam.esm.handler;

import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidFieldExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidFieldException invalidFieldException) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(invalidFieldException.getErrorMessage(), invalidFieldException.getErrorCode());
//        exceptionResponse.setErrorCode(httpStatus.value() + invalidFieldException.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
