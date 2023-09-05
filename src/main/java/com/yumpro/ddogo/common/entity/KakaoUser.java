package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 카카오 로그인 테스트용
@Getter
@Setter
@Entity
public class KakaoUser {

    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "kakao_account_id")
    private KakaoAccount kakao_account;

}
