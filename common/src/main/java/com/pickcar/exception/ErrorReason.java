package com.pickcar.exception;

public record ErrorReason(
        String errorCode,
        String reason
) {
}
