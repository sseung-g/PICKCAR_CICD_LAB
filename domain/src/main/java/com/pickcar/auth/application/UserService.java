package com.pickcar.auth.application;

import com.pickcar.auth.domain.User;
import com.pickcar.auth.domain.UserInfo;
import com.pickcar.auth.domain.UserRole;
import com.pickcar.auth.domain.UserStatus;
import com.pickcar.auth.infrastructure.UserRepository;
import com.pickcar.auth.presentation.dto.request.UserInfoRequest;
import com.pickcar.auth.presentation.dto.response.EmployeeListResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
    public void create(UserInfoRequest request, UserRole role) {
        if(userRepository.existsByInfoEmail(request.email())){ //TODO: 예외처리 하기
            throw new IllegalArgumentException("이미 사용 중인 email 입니다.");
        }

        User user = User.builder()
                .info(toUserInfoWithEncodedPassword(request))
                .role(role)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);
    }

    private UserInfo toUserInfoWithEncodedPassword(UserInfoRequest request) {
        return new UserInfo(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.phoneNumber()
        );
    }

    public List<User> getAllByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public List<EmployeeListResponse> getAllEmployees() {
        List<User> users = userRepository.findAllByRole(UserRole.EMPLOYEE);
        List<EmployeeListResponse> responses = new ArrayList<>();

        users.forEach(user -> {
                    EmployeeListResponse response = EmployeeListResponse.from(user);
                    responses.add(response);
                });

        return responses;
    }
}
