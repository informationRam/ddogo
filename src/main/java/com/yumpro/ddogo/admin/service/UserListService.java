package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.exception.DataNotFoundException;
import com.yumpro.ddogo.admin.repository.UserListRepository;
import com.yumpro.ddogo.admin.repository.UserModiRepository;
import com.yumpro.ddogo.admin.validation.UserModiAdmin;
import com.yumpro.ddogo.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserListService {
    private final UserListRepository userRepository;
    private final UserModiAdmin userModiAdmin;
    private final UserModiRepository userModiRepository;

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_no(user.getUser_no());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setUser_id(user.getUserId());
        userDTO.setBIRTH(user.getBirth());
        userDTO.setPwd(user.getPwd());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setJoin_date(user.getJoinDate());

        return userDTO;
    }

    public List<UserDTO> userList(Map<String,Object> map) {
        return userRepository.userList(map);
    }

    public User getUser(int userNo){
        Optional<User> user =userModiRepository.findById(userNo);
        if(user.isPresent()) {
            return user.get();
        } else {
            //사용자 정의 예외처리
            throw new DataNotFoundException();
        }
    }
}