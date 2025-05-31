package com.pickcar.presentation.dto.response;

import java.time.LocalDateTime;

public record ResponseInfo(
        Boolean isSuccess,
        Integer statusCode,
        LocalDateTime timeStamp
) {
    public static ResponseInfo success(Integer statusCode) {
        return new ResponseInfo(true, statusCode, LocalDateTime.now());
    }

    public static ResponseInfo error(Integer statusCode) {
        return new ResponseInfo(false, statusCode, LocalDateTime.now());
    }
}
