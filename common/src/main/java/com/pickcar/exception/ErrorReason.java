package com.pickcar.exception;


public record ErrorReason(
        Integer status,
        String code,
        String reason
) {
}
