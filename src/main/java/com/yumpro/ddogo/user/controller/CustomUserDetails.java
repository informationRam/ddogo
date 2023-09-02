package com.yumpro.ddogo.user.controller;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final String user_id;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String user_id) {
        super(username, password, authorities);
        this.user_id = user_id;
    }
    public String getUser_id() {
        return user_id;
    }
}
