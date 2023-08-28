package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserReprository;
import com.yumpro.ddogo.user.validation.UserCreateForm;
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
    public void userJoin(UserCreateForm userCreateForm){

        User user = new User();
        System.out.println("userjoin서비스진입!");
        user.setUser_name(userCreateForm.getUser_name());
        user.setUser_id(userCreateForm.getUser_id());
        user.setBirth(userCreateForm.getBirth());
        user.setGender(userCreateForm.getGender());
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(userCreateForm.getEmail());
        user.setPwd(userCreateForm.getPwd1());
        userReprository.save(user);
    }

    //로그인
   /* public void userlogin(String user_id, String pwd) {
        Optional<User> byUserId = userReprository.findByUser_id(user_id);
        System.out.println("userlogin :"+byUserId);
        Optional<User> byPwd = userReprository.findByPwd(pwd);
        System.out.println("byPwd :"+byPwd);

    }*/


}
