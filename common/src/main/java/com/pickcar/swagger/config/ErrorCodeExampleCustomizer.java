package com.pickcar.swagger.config;

import com.pickcar.exception.BaseErrorCode;
import com.pickcar.exception.ErrorReason;
import com.pickcar.presentation.dto.response.ErrorResponse;
import com.pickcar.swagger.annotation.ApiErrorCodeExample;
import com.pickcar.swagger.dto.ExampleHolder;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ErrorCodeExampleCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {

        ApiErrorCodeExample apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);

        if (apiErrorCodeExample != null) {
            generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
        }

        return operation;
    }

    private void generateErrorCodeResponseExample(Operation operation, Class<? extends BaseErrorCode> type) {
        ApiResponses responses = operation.getResponses();
        BaseErrorCode[] errorCodes = type.getEnumConstants();

        //Domain별로 작성한 ErrorCode에 대한 ExampleHolder를 모아 LIST + MAP 형태에 상태코드별로 그룹핑하여 담음
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
                .map(baseErrorCode -> {
                    try {
                        return ExampleHolder.builder()
                                .holder(getSwaggerExample(baseErrorCode.getExplainError(),
                                        baseErrorCode.getHttpStatusCode(), baseErrorCode.getErrorReason()))
                                .statusCode(baseErrorCode.getHttpStatusCode())
                                .name(baseErrorCode.getErrorReason().errorCode())
                                .build();
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);      //FIXME: 잡는 부분을 넣거나, 던지지 말고 다르게 처리
                    }
                }).collect(Collectors.groupingBy(ExampleHolder::statusCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    //윗 부분에서 도메인 별 에러코드들을 모두 모으는 역할이었다면, 여기서는 에러코드 각각 개별 내용을 Swagger용 Example 객체에 담음
    private Example getSwaggerExample(String value, Integer statusCode, ErrorReason errorReason) {
        ErrorResponse response = new ErrorResponse(statusCode, errorReason.errorCode(), errorReason.reason());
        Example example = new Example();

        example.description(value);
        example.setValue(response);

        return example;
    }

    //Swagger의 Example -> Response(응답 결과) 부분에 예외가 발생할 경우 오는 ErrorResponses를 담음 (맵에 담은 것 최종 적용)
    private void addExamplesToResponses(ApiResponses responses,
                                        Map<Integer, List<ExampleHolder>> exampleHoldersWithStatus) {

        exampleHoldersWithStatus.forEach((status, e) -> {
            Content content = new Content();
            MediaType mediaType = new MediaType();
            ApiResponse apiResponse = new ApiResponse();

            e.forEach(
                    exampleHolder -> mediaType.addExamples(exampleHolder.name(), exampleHolder.holder())
            );

            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);

            responses.addApiResponse(status.toString(), apiResponse);
        });
    }
}
