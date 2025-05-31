package com.pickcar.exception;

public interface BaseErrorCode {
    ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;

    Integer getHttpStatusCode();
}
