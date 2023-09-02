package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Page<User> findAll(Pageable pageable) throws DataAccessException;
}