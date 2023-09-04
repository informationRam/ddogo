package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> userList(Map<String,Object> map) {
        return userRepository.userList(map);
    }
}