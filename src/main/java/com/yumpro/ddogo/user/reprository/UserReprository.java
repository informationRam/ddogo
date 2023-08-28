package com.yumpro.ddogo.user.reprository;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReprository extends JpaRepository<User,Integer> {


    //아이디중복확인
/*    boolean existsByUser_id(String user_id);
    //이메일주소확인
    boolean existsByEmail(String email*/);


}
