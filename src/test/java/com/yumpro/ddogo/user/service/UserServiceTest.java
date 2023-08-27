package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserReprository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {


    @Autowired
    private UserReprository userReprository;
    @Test
    void userJoin() {
        User user = new User();
        Date date =
        System.out.println("userjoin서비스진입");
        user.setUser_name("송휘");
        user.setUser_id("아이스");
        user.setBirth();
        user.setGender("F");
        user.setJoinDate(LocalDateTime.now());
        user.setEmail("dd@naver.com");
        user.setPwd("1234");
        userReprository.save(user);


    }
}