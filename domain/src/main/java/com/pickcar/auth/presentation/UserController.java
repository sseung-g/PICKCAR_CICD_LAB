package com.pickcar.auth.presentation;

import com.pickcar.auth.application.UserService;
import com.pickcar.auth.presentation.dto.request.UserInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up/super-admins")
    public void createSuperAdmin(@RequestBody UserInfoRequest request) {
        userService.createSuperAdmin(request);
    }


    @PostMapping("/sign-up")
    public void createUser(@RequestBody UserInfoRequest request) {
        log.info("url : /sign-up");
        log.info("Request: {}",request);
        userService.createEmployee(request);
    }
}
