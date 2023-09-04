package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModiRepository extends JpaRepository<User,Integer> {
}
