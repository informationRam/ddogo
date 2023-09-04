package com.yumpro.ddogo.user.validation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class LoginVaildation {

    @NotEmpty(message = "아이디는 필수입력입니다.")
    private String user_id;            //'회원 아이디'

    @NotEmpty(message = "비밀번호는 필수입력입니다.")
    private String pwd;                //'비밀번호'

}
