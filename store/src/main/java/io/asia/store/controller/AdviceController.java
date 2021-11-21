package io.asia.store.controller;

import io.asia.store.domain.dto.FieldErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler({StaleObjectStateException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    void handleStaleObjectStateException(Exception e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<FieldErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return e.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    FieldError fieldError = (FieldError) objectError;
                    return new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(Collectors.toList());
    };

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<FieldErrorDto> handleMethodConstraintViolationException(javax.validation.ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return e.getConstraintViolations().stream()
                .map(constraintViolation -> new FieldErrorDto(null, constraintViolation.getMessage()))
                .collect(Collectors.toList());
    }
}
