package com.yumpro.ddogo.user.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

//(스프링시큐리티가) 인증 후 사용자에게 부여할 권한을 정의한 클래스
@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String value;

    public String getValue() {
        return value;
    }
}

