package com.example.mediflow.user.service;


import com.example.mediflow.auth.dto.UserResponse;
import com.example.mediflow.common.exception.UserNotFoundException;
import com.example.mediflow.user.entity.User;
import com.example.mediflow.user.entity.UserRole;
import com.example.mediflow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse assignRole(
            UUID userId,
            UserRole role
    ){
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + userId
                        )
                );

        user.setRole(role);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhone(),
                savedUser.getStatus(),
                savedUser.getCreatedAt(),
                savedUser.getRole()
        );
    }
}
