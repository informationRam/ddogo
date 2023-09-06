package com.yumpro.ddogo.kakao.service;

import com.yumpro.ddogo.kakao.entity.KakaoAccount;
import com.yumpro.ddogo.kakao.entity.KakaoUser;
import com.yumpro.ddogo.kakao.reprository.KakaouserReprository;
import com.yumpro.ddogo.user.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Kakaoservice {

    private final KakaouserReprository kakaouserReprository;
    public void add(String accessToken, KakaoAccount kakaoAccount) {
        System.out.println(" Kakaoservice 여기까지옴!");
        KakaoUser kakaouser = new KakaoUser();

        kakaouser.setGender(kakaoAccount.getGender());
        kakaouser.setEmail(kakaoAccount.getEmail());
        kakaouser.setAccessToken(accessToken);

        kakaouserReprository.save(kakaouser);
        System.out.println("ok");
    }

    public KakaoUser getUser(String emial){
        System.out.println("서비스의 getUser");
        Optional<KakaoUser> kakaouser = kakaouserReprository.findByEmail(emial);
        if (kakaouser.isPresent()) {
            return kakaouser.get();
        } else {
            return null;
        }
    }
}
