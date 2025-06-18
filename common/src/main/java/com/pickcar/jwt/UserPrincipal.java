package com.pickcar.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class UserPrincipal { //TODO: 제거하기
    private final Long id;
//    private final Long companyId;
    private final String role;
}