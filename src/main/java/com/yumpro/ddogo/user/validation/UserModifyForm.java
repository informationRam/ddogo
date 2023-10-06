package com.yumpro.ddogo.user.validation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

//유효성 검사용 클래스
@Getter
@Setter
public class UserModifyForm {

    private int user_no;

    private String user_name;          //'회원 이름'

    private String user_id;            //'회원 아이디'

    private Date birth;

    private String gender;             //'성별'

    private LocalDateTime joinDate;    //'가입일'

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(unique = true)
    private String email;              //'회원 이메일'

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자리 이상 숫자, 문자, 특수문자 각각 1개 이상 포함 되어야 합니다.")
    private String pwd1; //'비밀번호'

    @NotEmpty(message = "비밀번호확인은 필수입력입니다.")
    private String pwd2;                //'비밀번호'

}
