package com.yumpro.ddogo.kakao.service;

import com.yumpro.ddogo.kakao.entity.KakaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Kakaoservice kakaoservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KakaoUser kakaoUser = kakaoservice.getUser(username);
        if (kakaoUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return kakaoUser;
    }
}
