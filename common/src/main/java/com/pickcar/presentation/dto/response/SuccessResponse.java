package com.pickcar.presentation.dto.response;

public record SuccessResponse(
        ResponseInfo responseInfo,
        Object data
) {
    public SuccessResponse(Integer statusCode, Object data) {
        this(ResponseInfo.success(statusCode), data);
    }
}
