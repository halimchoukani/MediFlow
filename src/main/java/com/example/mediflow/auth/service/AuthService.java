package com.example.mediflow.auth.service;

import com.example.mediflow.auth.dto.RegisterRequest;
import com.example.mediflow.auth.dto.UserResponse;
import com.example.mediflow.user.entity.User;
import com.example.mediflow.user.entity.UserStatus;
import com.example.mediflow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String passwordHash =
                passwordEncoder.encode(request.password());

        User user = User.create(
                request.email(),
                passwordHash,
                request.firstName(),
                request.lastName(),
                request.phone()
        );

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhone(),
                savedUser.getStatus(),
                savedUser.getCreatedAt()
        );
    }
}