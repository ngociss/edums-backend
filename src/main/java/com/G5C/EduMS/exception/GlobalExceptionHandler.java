package com.G5C.EduMS.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.G5C.EduMS.dto.response.ResponseData;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================== BaseException & subclasses ====================

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseData<Object>> handleBaseException(
            BaseException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ResponseData.error(
                        ex.getHttpStatus().value(),
                        ex.getCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(NotFoundResourcesException.class)
    public ResponseEntity<ResponseData<Object>> handleNotFound(
            NotFoundResourcesException ex, HttpServletRequest request) {
        return handleBaseException(ex, request);
    }

    @ExceptionHandler(ExistingResourcesException.class)
    public ResponseEntity<ResponseData<Object>> handleExisting(
            ExistingResourcesException ex, HttpServletRequest request) {
        return handleBaseException(ex, request);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseData<Object>> handleInvalidData(
            InvalidDataException ex, HttpServletRequest request) {
        return handleBaseException(ex, request);
    }

    @ExceptionHandler(CannotDeleteException.class)
    public ResponseEntity<ResponseData<Object>> handleCannotDelete(
            CannotDeleteException ex, HttpServletRequest request) {
        return handleBaseException(ex, request);
    }

    // ==================== Validation ====================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<Object>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseData.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        request.getRequestURI(),
                        errors
                ));
    }

    // ==================== HTTP errors ====================

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseData<Object>> handleNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseData.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "INVALID_REQUEST_BODY",
                        "Invalid request body: " + ex.getMostSpecificCause().getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseData<Object>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ResponseData.error(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "METHOD_NOT_ALLOWED",
                        "Method " + ex.getMethod() + " is not supported for this endpoint",
                        request.getRequestURI()
                ));
    }

    // ==================== Fallback ====================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<Object>> handleGeneric(
            Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_SERVER_ERROR",
                        "An unexpected error occurred. Please contact support.",
                        request.getRequestURI()
                ));
    }
}