package com.example.hoteltourist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TouristUpdateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 120, message = "Name must be at most 120 characters")
        String name,

        @NotBlank(message = "Destination is required")
        @Size(max = 160, message = "Destination must be at most 160 characters")
        String destination,

        @NotBlank(message = "Contact number is required")
        @Pattern(regexp = "^\\+?[0-9]{7,15}$",
                message = "Contact number must contain 7 to 15 digits, optionally prefixed by +")
        String contactNumber,

        @NotBlank(message = "Email address is required")
        @Email(message = "Email address must be valid")
        @Size(max = 254, message = "Email address must be at most 254 characters")
        String email
) {
}
