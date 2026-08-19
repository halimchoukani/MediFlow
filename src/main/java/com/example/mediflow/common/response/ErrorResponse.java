package com.example.mediflow.common.response;

import java.time.OffsetDateTime;

public record ErrorResponse(
        int status,
        String code,
        String message,
        OffsetDateTime timestamp
) {
}
