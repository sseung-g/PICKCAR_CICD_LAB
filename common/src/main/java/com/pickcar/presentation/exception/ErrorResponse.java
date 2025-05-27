package com.pickcar.presentation.exception;


public record ErrorResponse(
        ErrorCode errorCode,
        String reason
) {
}
