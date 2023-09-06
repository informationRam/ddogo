package com.yumpro.ddogo.kakao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoData {

    @JsonProperty("authObj")
    private AuthObj authObj;

    @JsonProperty("kakao_account")
    private KakaoAccount kakao_account;

    // Getters and setters
    public AuthObj getAuthObj() {
        return authObj;
    }

    public void setAuthObj(AuthObj authObj) {
        this.authObj = authObj;
    }

    public KakaoAccount getKakao_account() {
        return kakao_account;
    }

    public void setKakao_account(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }
}
