package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.UserDTO;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserRepository {
    List<UserDTO> userList(Map<String,Object> map) throws DataAccessException;
}
