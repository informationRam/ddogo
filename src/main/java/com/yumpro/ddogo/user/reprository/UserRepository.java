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

   //아이디 찾기 - 이메일주소로
   Optional<User> findByEmail(String email);

   //비밀번호 찾기 - 아이디 && 이메일주소
   Optional<User> findByUserIdAndEmail(String user_id, String email);

}
