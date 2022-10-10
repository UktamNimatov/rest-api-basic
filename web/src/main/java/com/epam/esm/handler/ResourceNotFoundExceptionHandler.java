package com.epam.esm.handler;

import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceNotFoundException exception) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getErrorMessage(), exception.getErrorCode());
        exceptionResponse.setErrorCode(httpStatus.value() + exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
