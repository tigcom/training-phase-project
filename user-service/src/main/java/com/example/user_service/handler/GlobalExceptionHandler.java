package com.example.user_service.handler;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.context.MessageSource;
import com.example.user_service.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("user.alreadyExists",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), errorMessage), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("error.invalidInput",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("error.invalidInput",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("user.notfound",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResult(EmptyResultDataAccessException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("user.notfound",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(RuntimeException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("error.invalidInput",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Locale locale = request.getLocale();
        String errorMessage = messageSource.getMessage("error.general",  new Object[]{ex.getMessage()}, locale);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex,WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        Locale locale = request.getLocale();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = messageSource.getMessage("error.invalidInput",  new Object[]{error.getDefaultMessage()}, locale);
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
