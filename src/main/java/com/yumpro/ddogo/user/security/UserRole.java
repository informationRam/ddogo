package com.yumpro.ddogo.user.security;

import lombok.Getter;

//(스프링시큐리티가) 인증 후 권한
@Getter
public enum UserRole {
    //ADMIN은 "ROLE_ADMIN"을 값으로,
    //USER은 "ROLE_USER"을 값으로 가진다
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
