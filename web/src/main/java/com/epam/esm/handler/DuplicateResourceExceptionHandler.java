package com.epam.esm.handler;

import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DuplicateResourceExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponse> handle(DuplicateResourceException exception) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getErrorMessage(), exception.getErrorCode());
//        exceptionResponse.setErrorCode(httpStatus.value() + exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
