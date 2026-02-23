package com.flight.auth_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String tokenType;
}
