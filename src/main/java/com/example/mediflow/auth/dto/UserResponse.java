package com.example.mediflow.auth.dto;

import com.example.mediflow.user.entity.UserStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String phone,
        UserStatus status,
        OffsetDateTime createdAt
) {
}