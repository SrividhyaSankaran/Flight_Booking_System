package com.flight.auth_service.dto;

import java.util.*;

import lombok.*;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String status;
    private Set<String> roles;
    private Set<String> privileges;
}
