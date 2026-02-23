package com.flight.auth_service.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}