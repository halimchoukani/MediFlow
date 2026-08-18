package com.example.mediflow.auth.dto;


import java.util.UUID;

public record LoginResponse(
        UUID userId,
        String email,
        String accessToken,
        String tokenType
) {
}