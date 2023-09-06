package com.yumpro.ddogo.kakao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class KakaoAccount {


    @JsonProperty("profile_needs_agreement")
    private boolean profile_needs_agreement;

    @JsonProperty("profile_nickname_needs_agreement")
    private boolean profile_nickname_needs_agreement;

    @JsonProperty("profile_image_needs_agreement")
    private boolean profile_image_needs_agreement;

    @JsonProperty("profile")
    private Profile profile;

    @JsonProperty("name_needs_agreement")
    private boolean name_needs_agreement;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email_needs_agreement")
    private boolean email_needs_agreement;

    @JsonProperty("is_email_valid")
    private boolean is_email_valid;

    @JsonProperty("is_email_verified")
    private boolean is_email_verified;

    @JsonProperty("email")
    private String email;

    @JsonProperty("age_range_needs_agreement")
    private boolean age_range_needs_agreement;

    @JsonProperty("age_range")
    private String age_range;

    @JsonProperty("birthyear_needs_agreement")
    private boolean birthyear_needs_agreement;

    @JsonProperty("birthyear")
    private String birthyear;

    @JsonProperty("birthday_needs_agreement")
    private boolean birthday_needs_agreement;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("birthday_type")
    private String birthday_type;

    @JsonProperty("gender_needs_agreement")
    private boolean gender_needs_agreement;

    @JsonProperty("gender")
    private String gender;



    public static class Profile {
        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("thumbnail_image_url")
        private String thumbnail_image_url;

        @JsonProperty("profile_image_url")
        private String profile_image_url;

        @JsonProperty("is_default_image")
        private boolean is_default_image;

    }
}