package com.epam.esm.handler;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.constant.ConstantMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DaoExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<ExceptionResponse> handleDaoException(DaoException daoException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(daoException.getLocalizedMessage(), ConstantMessages.DAO_ERROR_MESSAGE);
        exceptionResponse.setErrorCode(httpStatus + ConstantMessages.DAO_ERROR_MESSAGE);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
