package com.yumpro.ddogo.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_no;            //'회원 번호'

    @Column(length = 200)
    private String user_name;          //'회원 이름'

    @Column(length = 200)
    private String user_id;            //'회원 아이디'

    private Date birth;              //'생년월일'

    private String gender;             //'성별'

    private LocalDateTime joinDate;    //'가입일'

    private String email;              //'회원 이메일'

    private String pwd;                //'비밀번호'


}
