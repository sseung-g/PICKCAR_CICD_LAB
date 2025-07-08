package com.pickcar.auth.presentation;

import com.pickcar.auth.application.TokenService;
import com.pickcar.auth.presentation.dto.response.AccessTokenResponse;
import com.pickcar.auth.presentation.dto.response.AuthResponse;
import com.pickcar.auth.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse reissueRefreshToken(HttpServletRequest request, HttpServletResponse response){
        log.info("Issued new AccessToken for refreshToken");
        String refreshToken = TokenUtils.extractRefreshTokenFromCookie(request);
        AuthResponse authResponse = tokenService.reissueTokens(refreshToken);
        TokenUtils.setRefreshTokenCookie(response, authResponse.refreshToken());
        return new AccessTokenResponse(authResponse.accessToken());
    }
}
