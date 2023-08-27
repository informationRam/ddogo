package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserReprository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReprository userReprository;

    //회원가입처리
    public void userJoin(User user){

        System.out.println("userjoin서비스진입");
        user.setUser_name(user.getUser_name());
        user.setUser_id(user.getUser_id());
        user.setBirth(user.getBirth());
        user.setGender(user.getGender());
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(user.getEmail());
        user.setPwd(user.getPwd());
        userReprository.save(user);
    }





}
