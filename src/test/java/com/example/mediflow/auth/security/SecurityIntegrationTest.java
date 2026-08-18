package com.example.mediflow.auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:18")
                    .withDatabaseName("mediflow_test")
                    .withUsername("test")
                    .withPassword("test");

    @LocalServerPort
    private int port;

    private RestClient client() {
        return RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void applicationContextShouldStart() {
        assertThat(port).isPositive();
    }

    @Test
    void shouldRejectUnauthenticatedRequest() {

        RestClient client = client();

        assertThatThrownBy(() ->
                client.get()
                        .uri("/api/v1/auth/me")
                        .retrieve()
                        .toBodilessEntity()
        )
                .isInstanceOf(RestClientResponseException.class)
                .satisfies(exception -> {
                    RestClientResponseException ex =
                            (RestClientResponseException) exception;

                    assertThat(ex.getStatusCode().value())
                            .isEqualTo(403);
                });
    }
}