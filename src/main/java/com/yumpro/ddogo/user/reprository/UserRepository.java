package com.yumpro.ddogo.user.reprository;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserid(String userid);



    //중복값 확인
    boolean existsByEmail(String email);


    //이메일주소
   /* Optional<User> findByuser_id(String user_id);*/

}
