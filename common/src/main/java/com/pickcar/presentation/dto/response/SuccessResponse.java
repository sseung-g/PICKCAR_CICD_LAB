package com.pickcar.presentation.dto.response;

import java.time.LocalDateTime;

public record SuccessResponse (
        Boolean success,
        Integer status,
        Object data,
        LocalDateTime timeStamp
) {
    public SuccessResponse(Integer status, Object data) {
        this(true, status, data, LocalDateTime.now());
    }
}
