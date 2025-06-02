package com.pickcar.presentation.controller;

import com.pickcar.application.EmulatorService;
import com.pickcar.application.EventInfoService;
import com.pickcar.presentation.dto.request.EmulatorRequest;
import com.pickcar.presentation.dto.request.EventInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmulatorApiController {

    EmulatorService emulatorService;
    private final EventInfoService eventInfoService;

    @PostMapping("/terminal")
    public void emulator(@RequestBody EmulatorRequest request) {
        log.info("POST /api/v1/engine - EmulatorRequest: {}", request);
        emulatorService.terminal(request);
    }

    @PostMapping("/engine/on")
    public void emulatorEngineOn(@RequestBody EventInfoRequest request) {
        log.info("POST /api/v1/engine/on - EventInfoRequest: {}", request);
        eventInfoService.on(request);
    }

    @PostMapping("/engine/off")
    public void emulatorEngineOff(@RequestBody EventInfoRequest request) {
        log.info("POST /api/v1/engine/off - EventInfoRequest: {}", request);
        eventInfoService.off(request);
    }
}
