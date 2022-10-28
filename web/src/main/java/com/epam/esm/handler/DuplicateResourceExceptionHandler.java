package com.epam.esm.handler;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.constant.ConstantMessages;
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
        String errorMessage = exception.getErrorMessage();
        if (errorMessage.contains(ConstantMessages.EXISTING_GIFT_CERTIFICATE_NAME) ||
                errorMessage.contains(ConstantMessages.EXISTING_TAG_NAME)) {
            errorMessage = Translator.toLocale(exception.getErrorMessage());
        }
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(errorMessage, exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
