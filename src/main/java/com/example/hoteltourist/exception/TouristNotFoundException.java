package com.example.hoteltourist.exception;

public class TouristNotFoundException extends RuntimeException {

    public TouristNotFoundException(Long id) {
        super("Tourist not found: " + id);
    }
}
