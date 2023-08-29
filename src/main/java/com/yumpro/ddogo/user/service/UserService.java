package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.validation.UserCreateForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입처리
    public void userJoin(UserCreateForm userCreateForm){

        User user = new User();
        System.out.println("userjoin서비스진입!");
        user.setUser_name(userCreateForm.getUser_name());
        user.setUserid(userCreateForm.getUser_id());
        user.setBirth(userCreateForm.getBirth());
        user.setGender(userCreateForm.getGender());
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(userCreateForm.getEmail());
        user.setPwd(userCreateForm.getPwd1());
        userRepository.save(user);
    }

    //로그인
   /* public void userlogin(String user_id, String pwd) {
        Optional<User> byUserId = userReprository.findByUser_id(user_id);
        System.out.println("userlogin :"+byUserId);
        Optional<User> byPwd = userReprository.findByPwd(pwd);
        System.out.println("byPwd :"+byPwd);

    }*/


  // 아이디, 이메일 중복 여부 확인
    @Transactional(readOnly = true)
    public boolean checkUserIdDuplication(String user_id) {
        boolean usernameDuplicate = userRepository.existsByUserid(user_id);
        return usernameDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }


}
