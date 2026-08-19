package com.example.mediflow.auth.dto;

import com.example.mediflow.user.entity.UserRole;
import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
        @NotNull
        UserRole role
) {
}