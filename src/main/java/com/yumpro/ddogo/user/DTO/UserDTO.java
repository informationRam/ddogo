package com.yumpro.ddogo.user.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class UserDTO {

    private String user_name;          //'회원 이름'

    private String user_id;            //'회원 아이디'

    private Date birth;

    private String gender;             //'성별'

    private LocalDateTime joinDate;    //'가입일'

    private String email;              //'회원 이메일'

    private String pwd;                //'비밀번호'


}
