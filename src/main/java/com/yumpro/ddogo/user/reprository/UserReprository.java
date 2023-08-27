package com.yumpro.ddogo.user.reprository;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReprository extends JpaRepository<User,Integer> {

}
