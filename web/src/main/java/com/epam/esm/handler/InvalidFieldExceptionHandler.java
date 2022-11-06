package com.epam.esm.handler;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.*;

@RestControllerAdvice
public class InvalidFieldExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidFieldException invalidFieldException) {
        String errorMessage = invalidFieldException.getErrorMessage();
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_TAG)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_TAG,
                    Translator.toLocale(ConstantMessages.INVALID_TAG));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE));
        }
        if (errorMessage.contains(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE)) {
            errorMessage = errorMessage.replace(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE,
                    Translator.toLocale(ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE));
        }
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(errorMessage, invalidFieldException.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
