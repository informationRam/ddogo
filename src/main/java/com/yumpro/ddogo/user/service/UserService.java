package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserReprository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReprository userReprository;

    //회원가입처리
    public void userJoin(String user_name, String user_id, Date birth,
                         String gender,String email,String pwd){
        User user = new User();
        user.setUser_name(user_name);
        user.setUser_id(user_id);
        user.setBirth(birth);
        user.setGender(gender);
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(email);
        user.setPwd(pwd);
        userReprository.save(user);
    }



}
