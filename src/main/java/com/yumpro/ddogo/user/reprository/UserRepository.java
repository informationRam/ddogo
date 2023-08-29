package com.yumpro.ddogo.user.reprository;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //회원가입시 중복값 아이디 확인
    boolean existsByUserId(String userId);

    //회원가입시 중복값 이메일 확인
    boolean existsByEmail(String email);

    //아이디로 값 찾기
   Optional<User> findByUserId(String userId);

}
