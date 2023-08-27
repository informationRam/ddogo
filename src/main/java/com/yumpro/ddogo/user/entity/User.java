package com.yumpro.ddogo.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int user_no;            //'회원 번호'

    @Column(length = 200)
    private String user_name;          //'회원 이름'

    @Column(length = 200)
    private String user_id;            //'회원 아이디'

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;             //'성별'

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime joinDate;    //'가입일'

    private String email;              //'회원 이메일'

    private String pwd;                //'비밀번호'


}
