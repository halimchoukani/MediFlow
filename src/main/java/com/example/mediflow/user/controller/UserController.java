package com.example.mediflow.user.controller;


import com.example.mediflow.auth.dto.UserResponse;
import com.example.mediflow.user.dto.AssignRoleRequest;
import com.example.mediflow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> assignRole(
            @PathVariable UUID userId,
            @Valid @RequestBody AssignRoleRequest request
    ) {
        return ResponseEntity.ok(
                userService.assignRole(
                        userId,
                        request.role()
                )
        );
    }

    @PatchMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deactivateUser(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.deactivateUser(userId);

        return ResponseEntity.ok(response);
    }
}
