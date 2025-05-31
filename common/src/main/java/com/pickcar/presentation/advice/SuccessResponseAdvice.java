package com.pickcar.presentation.advice;

import com.pickcar.presentation.dto.response.SuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {
        "com.pickcar.auth.presentation",
        "com.pickcar.company.presentation",
        "com.pickcar.drivehistory.presentation",
        "com.pickcar.rental.presentation",
        "com.pickcar.reservation.presentation",
        "com.pickcar.vehicle.presentation"
})
public class SuccessResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //basePackage와 supports()의 목적은 거의 같지만, 패키지 기반 분리냐, 클래스 기반 분리냐의 차이(+ 유연성)
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);

        if (resolve == null) {
            return body;
        }

        if (resolve.equals(HttpStatus.NO_CONTENT)) {
            return null;        //FIXME: null 반환 안정성 고려 필요
        }

        if (resolve.is2xxSuccessful()) {
            return new SuccessResponse(status, body);
        }

        return body;
    }
}
