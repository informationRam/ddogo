package com.yumpro.ddogo.kakao.reprository;

import com.yumpro.ddogo.kakao.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaouserReprository extends JpaRepository<KakaoUser, Integer> {

    Optional<KakaoUser> findByEmail(String email);


}
