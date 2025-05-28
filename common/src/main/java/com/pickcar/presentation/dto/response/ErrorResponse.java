package com.pickcar.presentation.dto.response;

import com.pickcar.exception.ErrorReason;

public record ErrorResponse(
        ResponseInfo responseInfo,
        ErrorReason errorReason
) {
    public ErrorResponse(Integer statusCode, String errorCode, String reason) {
        this(ResponseInfo.error(statusCode), new ErrorReason(errorCode, reason));
    }

    public ErrorResponse(Integer httpStatusCode, ErrorReason errorReason) {
        this(ResponseInfo.error(httpStatusCode), errorReason);
    }
}
