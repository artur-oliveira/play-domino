package org.playdomino.exceptions;

import com.fasterxml.jackson.core.JacksonException;
import lombok.extern.slf4j.Slf4j;
import org.playdomino.models.generic.GenericError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DominoException.class)
    protected ResponseEntity<GenericError> handleException(DominoException ex) {
        return new ResponseEntity<>(GenericError.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<GenericError> handleException(AuthenticationException ex) {
        return new ResponseEntity<>(GenericError
                .builder()
                .error(ex.getClass().getSimpleName())
                .message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<GenericError> handleException(PropertyReferenceException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<GenericError> handleException(HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message(ex.getMessage()).build(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<GenericError> handleException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message("database error. cannot create, update or delete this resource").build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message("Request Error").data(ex.getBindingResult().getAllErrors()).build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericError> handleValidationExceptions(HttpMessageNotReadableException ex) {
        if (Objects.nonNull(ex.getCause()) && ex.getCause() instanceof JacksonException causeEx) {
            return new ResponseEntity<>(GenericError.builder().error(causeEx.getClass().getSimpleName()).message(causeEx.getOriginalMessage()).build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message("Required request body is missing").build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<GenericError> handleException(Exception ex) {
        log.error("Internal Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(GenericError.builder().error(ex.getClass().getSimpleName()).message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
