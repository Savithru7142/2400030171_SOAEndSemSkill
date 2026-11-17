package com.example.hoteltourist.controller;

import com.example.hoteltourist.dto.ApiResponse;
import com.example.hoteltourist.dto.TouristUpdateRequest;
import com.example.hoteltourist.security.RequestTokenValidator;
import com.example.hoteltourist.service.TouristService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tourist")
public class TouristController {

    private static final String REQUEST_TOKEN_HEADER = "X-Request-Token";

    private final TouristService touristService;
    private final RequestTokenValidator tokenValidator;

    public TouristController(TouristService touristService, RequestTokenValidator tokenValidator) {
        this.touristService = touristService;
        this.tokenValidator = tokenValidator;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing tourist",
            description = "Updates tourist details only when the configured request token is valid.",
            security = @SecurityRequirement(name = "RequestToken")
    )
    public ResponseEntity<ApiResponse> updateTourist(
            @PathVariable Long id,
            @RequestHeader(value = REQUEST_TOKEN_HEADER, required = false) String requestToken,
            @Valid @RequestBody TouristUpdateRequest request) {
        tokenValidator.validate(requestToken);
        touristService.updateTourist(id, request);
        return ResponseEntity.ok(new ApiResponse("Tourist updated successfully"));
    }
}
