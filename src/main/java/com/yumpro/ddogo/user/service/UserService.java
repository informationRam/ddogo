package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.user.DTO.UserDTO;
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
        user.setIsshow("Y");
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
    public String searchId(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserId();
        } else {
            return null; // 해당 이메일을 가진 사용자가 없을 경우
        }
    }

//비밀번호찾기
    public boolean pwdsearch(User user){
        Optional<User> byUserIdAndEmail = userRepository.findByUserIdAndEmail(user.getUserId(), user.getEmail());
        if(byUserIdAndEmail.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    //DTO -> Entity로 변경
    public User toEntity(UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUser_id());
        user.setUser_name(userDTO.getUser_name());
        user.setBirth(userDTO.getBirth());
        user.setGender(userDTO.getGender());
        user.setJoinDate(userDTO.getJoinDate());
        user.setEmail(userDTO.getEmail());
        user.setPwd(user.getPwd());
        return user;

    }

    //아이디값 넣어서 회원정보 가져오기
    public Optional<User> getUser(String user_id){
        Optional<User> user = userRepository.findByUserId(user_id);
        return user;
    }

    // 비번 변경
        public void userpwdModify(User user,String tempPassword){
            user.setUser_no(user.getUser_no());
            user.setUser_name(user.getUser_name());
            user.setUserId(user.getUserId());
            user.setBirth(user.getBirth());
            user.setGender(user.getGender());
            user.setJoinDate(user.getJoinDate());
            user.setEmail(user.getEmail());
            user.setPwd(PasswordEncoder.encode(tempPassword));
            user.setIsshow("Y");
            userRepository.save(user);
    }


}
