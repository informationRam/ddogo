package com.yumpro.ddogo.user.reprository;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReprository extends JpaRepository<User,Integer> {
}
