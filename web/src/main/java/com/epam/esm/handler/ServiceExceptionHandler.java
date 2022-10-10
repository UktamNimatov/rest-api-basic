package com.epam.esm.handler;

import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.constant.ConstantMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleServiceException(ServiceException serviceException) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(serviceException.getLocalizedMessage(), ConstantMessages.SERVICE_ERROR_MESSAGE);
        exceptionResponse.setErrorCode(httpStatus + ConstantMessages.SERVICE_ERROR_MESSAGE);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
