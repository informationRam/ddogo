package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.validation.UserCreateForm;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder PasswordEncoder;

    //회원가입처리
    public void userJoin(UserCreateForm userCreateForm){
        User user = new User();
        System.out.println("userjoin서비스진입!");
        user.setUser_name(userCreateForm.getUser_name());
        user.setUserId(userCreateForm.getUser_id());
        user.setBirth(userCreateForm.getBirth());
        user.setGender(userCreateForm.getGender());
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(userCreateForm.getEmail());
        user.setPwd(userCreateForm.getPwd1());
        user.setPwd(PasswordEncoder.encode(userCreateForm.getPwd1()));
        userRepository.save(user);
    }

  // 회원 가입시 아이디, 이메일 중복 여부 확인
    @Transactional(readOnly = true)
    public boolean checkUserIdDuplication(String user_id) {
        boolean usernameDuplicate = userRepository.existsByUserId(user_id);
        return usernameDuplicate;
    }

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }


    //아이디 찾기
    public String searchId(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(!byEmail.isPresent()){
            return "null";
        }else {
            return byEmail.get().getEmail();
        }
    }




}
