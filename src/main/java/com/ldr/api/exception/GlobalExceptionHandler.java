package com.ldr.api.exception;

import com.ldr.api.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "RESOURCE_NOT_FOUND",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "VALIDATION_ERROR",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "BUSINESS_ERROR",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "DUPLICATE_RESOURCE",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "UNAUTHORIZED",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            ex.getMessage(),
            "FORBIDDEN",
            null,
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "An unexpected error occurred",
            "INTERNAL_SERVER_ERROR",
            Arrays.asList(ex.getMessage()),
            request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
