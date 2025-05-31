package com.pickcar.drivehistory.presentation;

import com.pickcar.drivehistory.application.DriveHistoryService;
import com.pickcar.drivehistory.presentation.dto.response.ExampleResponse;
import com.pickcar.presentation.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO: Controller를 Domain이 아닌 다른 모듈로 분리하는 것은 어떨까?
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class DriveHistoryController implements DriveHistoryApiDocs {

    private final DriveHistoryService driveHistoryService;

    @Override
    @GetMapping("/example")
    public ResponseEntity<ExampleResponse> example() {
        return ResponseEntity.ok(new ExampleResponse("example"));       //Fixme: 실제로는 서비스에서 구성되어야 함
    }
}
