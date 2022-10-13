package com.epam.esm.handler;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.exception.ExceptionResponse;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceNotFoundException exception) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(exception.getErrorMessage(), exception.getErrorCode());
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<ExceptionResponse> handle404(NoHandlerFoundException exception) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(),
//                String.valueOf(ConstantMessages.ERROR_CODE_404));
//        return new ResponseEntity<>(exceptionResponse, httpStatus);
//    }

//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<ExceptionResponse> handle405(NoHandlerFoundException exception) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(),
//                String.valueOf(ConstantMessages.ERROR_CODE_405));
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
//    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_404));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
         ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_405));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    /*methods below are of secondary importance*/
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
                String.valueOf(ConstantMessages.ERROR_CODE_400));
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
