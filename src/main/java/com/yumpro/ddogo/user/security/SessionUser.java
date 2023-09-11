package com.yumpro.ddogo.user.security;

import com.yumpro.ddogo.common.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {

    private String name;
    private String email;
    private String profile_yn;

    public SessionUser(User user){
        this.name = user.getUser_name();
        this.email = user.getEmail();
    }
}
