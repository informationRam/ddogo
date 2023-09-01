package com.yumpro.ddogo.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private int user_no;            //'회원 번호'

    @Column(length = 200)
    private String user_name;          //'회원 이름'

    @Column(unique = true , name ="user_id")
    private String userId;            //'회원 아이디'

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;             //'성별'

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime joinDate;    //'가입일'

    @Column(unique = true)
    private String email;              //'회원 이메일'

    @NotNull
    private String pwd;                //'비밀번호'

}
