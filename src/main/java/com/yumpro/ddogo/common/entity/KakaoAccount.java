package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class KakaoAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // KakaoAccount의 식별자

    @Column
    private String profileNickname;

    @Column
    private String gender;

    @Column
    private String ageRange;

    @Column
    private String birthday;

    @Column
    private String email;

}