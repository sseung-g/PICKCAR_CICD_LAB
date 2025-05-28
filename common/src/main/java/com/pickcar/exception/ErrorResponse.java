package com.pickcar.exception;


import java.time.LocalDateTime;

public record ErrorResponse(
        Boolean success,
        Integer status,
        String code,
        String reason,
        LocalDateTime timeStamp
) {
    public ErrorResponse(Integer status, String code, String reason) {
        this(false, status, code, reason, LocalDateTime.now());
    }
}
