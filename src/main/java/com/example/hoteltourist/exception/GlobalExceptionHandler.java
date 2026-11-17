package com.example.hoteltourist.exception;

import com.example.hoteltourist.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidRequestTokenException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequestToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Invalid or missing request token"));
    }

    @ExceptionHandler(TouristNotFoundException.class)
    public ResponseEntity<ApiResponse> handleTouristNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Tourist not found"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse> handleInvalidRequest() {
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid request body"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedError(Exception exception) {
        logger.error("Unexpected error while handling request", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Internal server error"));
    }
}
