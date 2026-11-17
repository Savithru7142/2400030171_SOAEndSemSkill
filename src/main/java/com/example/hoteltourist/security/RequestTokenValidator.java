package com.example.hoteltourist.security;

import com.example.hoteltourist.exception.InvalidRequestTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class RequestTokenValidator {

    private final String configuredToken;

    public RequestTokenValidator(@Value("${tourist.api.token}") String configuredToken) {
        if (configuredToken.isBlank()) {
            throw new IllegalStateException("The TOURIST_API_TOKEN configuration must not be blank");
        }
        this.configuredToken = configuredToken;
    }

    public void validate(String receivedToken) {
        if (receivedToken == null || receivedToken.isBlank()
                || !MessageDigest.isEqual(
                        configuredToken.getBytes(StandardCharsets.UTF_8),
                        receivedToken.getBytes(StandardCharsets.UTF_8))) {
            throw new InvalidRequestTokenException();
        }
    }
}
