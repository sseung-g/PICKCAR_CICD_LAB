package com.pickcar.presentation.advice;

import com.pickcar.constants.GlobalStatic;
import com.pickcar.exception.GlobalException;
import com.pickcar.presentation.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorResponseAdvice {

    @ExceptionHandler(GlobalException.class)     //NOTE: 특정 도메인에 대한 핸들링으로도 변경 가능
    public ResponseEntity<ErrorResponse> handleException(GlobalException e, HttpServletRequest request) {
        log.error(GlobalStatic.ERROR_PREFIX + " ErrorCode : {}, URI {}, Message : ",
                e.getErrorCode(), request.getRequestURI(), e.getErrorReason());

        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatusCode(), e.getErrorReason());

        return ResponseEntity.status(e.getHttpStatusCode()).body(errorResponse);
    }
}
