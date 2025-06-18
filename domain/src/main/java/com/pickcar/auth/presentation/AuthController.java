package com.pickcar.auth.presentation;

import com.pickcar.auth.application.AuthService;
import com.pickcar.auth.presentation.dto.request.AuthRequest;
import com.pickcar.auth.presentation.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.login(request.email(), request.password());
        return new AuthResponse(token);
    }
}