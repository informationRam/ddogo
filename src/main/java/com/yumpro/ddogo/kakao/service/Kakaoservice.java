package com.yumpro.ddogo.kakao.service;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.kakao.entity.KakaoUser;
import com.yumpro.ddogo.kakao.reprository.KakaouserReprository;
import com.yumpro.ddogo.mail.service.EmailService;
import com.yumpro.ddogo.user.reprository.UserRepository;
import com.yumpro.ddogo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Kakaoservice {

    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder PasswordEncoder;
    private final KakaouserReprository kakaouserReprository;

    // ———————— 카카오  서비스————————————

    /*//kakao회원가입처리
    public void kakaoJoin(String accessToken, KakaoAccount kakaoAccount) throws ParseException, java.text.ParseException {
        System.out.println("kakaouserjoin서비스진입!");
        User user = new User();
        //받아오지 못한 기본값들 세팅
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date defaultBirthDate = dateFormat.parse("9999-01-01");

        // 랜덤 아이디 생성
        String randomID = null;
        boolean isUniqueId = false;


        while (!isUniqueId) {
            randomID = emailService.generateRandomID(); //랜덤 아이디 중복 없을때 저장
            isUniqueId = !userService.checkUserIdDuplication(randomID);
        }

        String gender = kakaoAccount.getGender();
        System.out.println("gender:" +gender);
        // 성별 받아온 값 첫자리 F,M 으로 출력하기 -> 에러는 E로 저장
        if (gender != null && !gender.isEmpty()) {
            String firstCharacter = String.valueOf(gender.charAt(0));
            System.out.println("firstCharacter : "+firstCharacter);
            if (firstCharacter.equalsIgnoreCase("f")) {
                user.setGender("F");
            } else if (firstCharacter.equalsIgnoreCase("m")) {
                user.setGender("M");
            } else {
                user.setGender("E"); // 에러
            }
        } else {
            user.setGender("E"); // 에러
        }

        user.setUser_name(randomID);
        user.setUserId(randomID);
        user.setBirth(defaultBirthDate);
        user.setJoinDate(LocalDateTime.now());
        user.setEmail(kakaoAccount.getEmail());
        user.setPwd(PasswordEncoder.encode(emailService.getTempPassword()));    //비밀번호도 랜덤으로 저장
        userRepository.save(user);
    }

    public KakaoUser getUser(String email) {
        Optional<KakaoUser> kakaoUser = kakaouserReprository.findByEmail(email);
        if(kakaoUser.isPresent()){
            return kakaoUser.get();
        }else {
            return null;
        }
    }

    public void add(String accessToken, KakaoAccount kakaoAccount) {
        KakaoUser kakaoUser = new KakaoUser();
        kakaoUser.setEmail(accessToken);
        kakaoUser.setEmail(kakaoAccount.getEmail());
        kakaoUser.setGender(kakaoAccount.getGender());
        kakaouserReprository.save(kakaoUser);
    }*/
}
