package com.pickcar.auth.application;

import com.pickcar.auth.domain.User;
import com.pickcar.auth.infrastructure.UserRepository;
import com.pickcar.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService { 

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) { //TODO: 메소드명 변경하기
        User user = findUserByEmail(email);
        validatePassword(password, user.getInfo().getPassword());
        return generateAuthToken(user);
    }

    private User findUserByEmail(String email){ //TODO: 예외처리 하기
        return userRepository.findByInfoEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("입력한 이메일이 유효하지 않거나 존재하지 않는 사용자입니다."));
    }

    private void validatePassword(String rawPassword, String encodedPassword){
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");//TODO: 예외처리 하기
        }
    }

    private String generateAuthToken(User user) {
        return jwtProvider.generateToken(
                user.getId(),
                user.getInfo().getName(),
                user.getRole().name()
        );
    }
}

