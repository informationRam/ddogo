package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.entity.User;
import com.yumpro.ddogo.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //DTO -> Entity로 변환
    public User toEntity(UserDTO userDTO){
        User user = new User();

        user.setUser_id(userDTO.getUser_id());
        user.setUser_no(userDTO.getUser_no());
        user.setUser_name(user.getUser_name());
        user.setEmail(user.getEmail());
        user.setBIRTH(userDTO.getBIRTH());
        user.setJoin_date(userDTO.getJoin_date());
        user.setGender(userDTO.getGender());
        user.setPwd(userDTO.getPwd());

        return user;
        }

        public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setUser_id(user.getUser_id());
        userDTO.setUser_no(user.getUser_no());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setEmail(user.getEmail());
        userDTO.setBIRTH(user.getBIRTH());
        userDTO.setJoin_date(user.getJoin_date());
        userDTO.setGender(user.getGender());
        userDTO.setPwd(user.getPwd());

        return userDTO;
        }
    public Page<User> getList(int page) {

        //질문목록조회 : findAll(0)
        int size=20;
        List<Sort.Order> sorts =new ArrayList();
        sorts.add(Sort.Order.desc("join_date"));
        Pageable pageable = PageRequest.of(page,size, Sort.by(sorts));//(page,size,sort)
        Page<User> userList = userRepository.findAll(pageable);
        return userList;
    }
}
