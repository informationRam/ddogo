package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.user.DTO.UserDTO;
import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.validation.UserCreateForm;

import com.yumpro.ddogo.user.validation.UserModifyForm;
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

    // 회원 가입시 아이디 중복 여부 확인
    @Transactional(readOnly = true)
    public boolean checkUserIdDuplication(String user_id) {
        System.out.println("서비스들어옴");
        boolean emailDuplicate = userRepository.existsByUserId(user_id);
        return emailDuplicate;
    }

    // 회원 가입시 이메일 중복 여부 확인 or id 찾기에 사용
    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        System.out.println("서비스들어옴");
        boolean emailDuplicate = userRepository.existsByEmail(email);
        return emailDuplicate;
    }


    //정보 수정시 사용
    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(User user,UserModifyForm userModifyForm) {
        boolean emailDuplicate = false;
        if(!user.getEmail().equals(userModifyForm.getEmail())){     //기존 이메일값, 변경한 이메일값이 다르면
            emailDuplicate = userRepository.existsByEmail(userModifyForm.getEmail());   //회원의 이메일값과 비교를 한다.
        }
        return emailDuplicate;
    }

    //아이디 찾기
    public String searchId(String email) {
        System.out.println("searchId서비스 hi");
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUserId();
        } else {
            return null; // 해당 이메일을 가진 사용자가 없을 경우
        }
    }

//비밀번호찾기 - id && email값 동시에 일치하는 회원이 있는지
    public boolean pwdsearch(String user_id,String email){
        System.out.println("pwdsearch 서비스옴!");
        Optional<User> byUserIdAndEmail = userRepository.findByUserIdAndEmail(user_id,email);
        System.out.println("user_id: "+user_id);
        System.out.println("email: "+email);
        System.out.println("byUserIdAndEmail.isEmpty()?"+byUserIdAndEmail.isEmpty());
        if(byUserIdAndEmail.isEmpty()){   //값이 비어있다면
            return true;
        }else {
            return false;
        }
    }

    //아이디값 넣어서 회원정보 가져오기
    public User getUser(String user_id){
        System.out.println("getUser진입");
        Optional<User> user = userRepository.findByUserId(user_id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    // 비번 변경
        public void userpwdModify(User user, String tempPassword){
            user.setPwd(PasswordEncoder.encode(tempPassword));
            userRepository.save(user);
    }

    // 회원 정보 변경 -> email,pwd
    public void userModify(User user, UserModifyForm userModifyForm) {
            user.setEmail(userModifyForm.getEmail());
            user.setPwd(PasswordEncoder.encode(userModifyForm.getPwd1()));
            userRepository.save(user);
    }
//회원탈퇴
    public void userDelete(User user){
        userRepository.delete(user);
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

    //user -> userDTO
    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUserId());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setBirth(user.getBirth());
        userDTO.setGender(user.getGender());
        userDTO.setJoinDate(user.getJoinDate());
        userDTO.setEmail(user.getEmail());
        userDTO.setPwd(user.getPwd());
        return userDTO;
    }

    // user -> UserCreateForm 변경
    public UserCreateForm toUserCreateForm(String user_id){
        Optional<User> user = userRepository.findByUserId(user_id);
        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setUser_name(user.get().getUser_name());
        userCreateForm.setUser_id(user.get().getUserId());
        userCreateForm.setBirth(user.get().getBirth());
        userCreateForm.setGender(user.get().getGender());
        userCreateForm.setEmail(user.get().getEmail());
        return userCreateForm;
    }

    //정보수정을 위한 인증 user -> touserModifyForm로 변경
    public UserModifyForm touserModifyForm(User user) {
        UserModifyForm userModifyForm = new UserModifyForm();
        userModifyForm.setUser_name(user.getUser_name());
        userModifyForm.setUser_id(user.getUserId());
        userModifyForm.setBirth(user.getBirth());
        userModifyForm.setGender(user.getGender());
        userModifyForm.setEmail(user.getEmail());
        return userModifyForm;
    }

    //인증값 확인, pwd1,pwd2 전달
    public UserModifyForm touserModifyForm(User user,String pwd1,String pwd2,String email) {
        UserModifyForm userModifyForm = new UserModifyForm();
        userModifyForm.setUser_name(user.getUser_name());
        userModifyForm.setUser_id(user.getUserId());
        userModifyForm.setBirth(user.getBirth());
        userModifyForm.setGender(user.getGender());
        userModifyForm.setEmail(email);
        userModifyForm.setPwd1(pwd1);
        userModifyForm.setPwd2(pwd2);
        return userModifyForm;
    }
}
