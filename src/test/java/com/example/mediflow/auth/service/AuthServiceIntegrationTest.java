package com.example.mediflow.auth.service;

import com.example.mediflow.auth.dto.RegisterRequest;
import com.example.mediflow.auth.dto.UserResponse;
import com.example.mediflow.user.entity.User;
import com.example.mediflow.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class AuthServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:18")
                    .withDatabaseName("mediflow_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
        );

        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );

        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );
    }

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldRegisterUser() {

        RegisterRequest request = new RegisterRequest(
                "john@example.com",
                "StrongPassword123",
                "John",
                "Doe",
                "+21612345678"
        );

        UserResponse response = authService.register(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.email())
                .isEqualTo("john@example.com");

        assertThat(response.firstName())
                .isEqualTo("John");

        assertThat(response.lastName())
                .isEqualTo("Doe");

        assertThat(response.status())
                .isEqualTo(com.example.mediflow.user.entity.UserStatus.ACTIVE);

        User savedUser = userRepository
                .findById(response.id())
                .orElseThrow();

        assertThat(savedUser.getPasswordHash())
                .isNotEqualTo("StrongPassword123");
    }

    @Test
    void shouldRejectDuplicateEmail() {

        RegisterRequest request = new RegisterRequest(
                "duplicate@example.com",
                "StrongPassword123",
                "John",
                "Doe",
                "+21612345678"
        );

        authService.register(request);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                        () -> authService.register(request)
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email is already registered");
    }

    @Test
    void shouldHashPassword() {

        RegisterRequest request = new RegisterRequest(
                "security@example.com",
                "MySuperSecret123",
                "Security",
                "Test",
                null
        );

        UserResponse response = authService.register(request);

        User user = userRepository
                .findById(response.id())
                .orElseThrow();

        assertThat(user.getPasswordHash())
                .isNotEqualTo(request.password());

        assertThat(user.getPasswordHash())
                .startsWith("$2");
    }
}