package com.pickcar.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }

    public Integer getHttpStatusCode() {
        return this.errorCode.getHttpStatusCode();
    }
}
