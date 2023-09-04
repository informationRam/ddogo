package com.yumpro.ddogo.admin.validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@Component
public class UserModiAdmin {
    private int user_no;

    @Size(min = 2, max = 5)
    @NotEmpty(message = "이름은 필수입력입니다.")
    private String user_name;          //'회원 이름'

    @NotEmpty(message = "아이디는 필수입력입니다.")
    private String user_id;            //'회원 아이디'

    @NotNull(message = "생년월일은 필수입력입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date BIRTH;

    @NotEmpty(message = "성별은 필수선택입니다.")
    private String gender;             //'성별'

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;              //'회원 이메일'

    private LocalDateTime join_date;    //'가입일'
}
