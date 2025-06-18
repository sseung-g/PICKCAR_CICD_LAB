package com.pickcar.auth.application;

import com.pickcar.auth.domain.User;
import com.pickcar.auth.domain.UserInfo;
import com.pickcar.auth.domain.UserRole;
import com.pickcar.auth.domain.UserStatus;
import com.pickcar.auth.infrastructure.UserRepository;
import com.pickcar.auth.presentation.dto.request.UserInfoRequest;
import com.pickcar.jwt.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] User Not Found By Id " + id));
    }

    @Transactional
    public void createEmployee(UserInfoRequest request) {
        checkDuplicateEmail(request.email());
        User user = User.builder()
                .info(toUserInfoWithEncodedPassword(request))
                .role(UserRole.EMPLOYEE)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void createSuperAdmin(UserInfoRequest request) {
        checkDuplicateEmail(request.email());
        User admin = User.builder()
                .info(toUserInfoWithEncodedPassword(request))
                .role(UserRole.SUPER_ADMIN)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(admin);
    }

    private void checkDuplicateEmail(String email){ //TODO: 예외처리 하기
        if(userRepository.existsByInfoEmail(email)){
            throw new IllegalArgumentException("이미 사용 중인 email 입니다.");
        }
    }

    private UserInfo toUserInfoWithEncodedPassword(UserInfoRequest request) {
        return new UserInfo(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.phoneNumber()
        );
    }
}
