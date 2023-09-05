package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.common.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface UserListRepository {
    List<UserDTO> userList(Map<String,Object> map) throws DataAccessException;
}
