package com.yumpro.ddogo.user.validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


//유효성 검사용 클래스
@Getter
@Setter
public class UserCreateForm {

    private int user_no;

   @Size(min = 2, max = 5)
   @NotEmpty(message = "이름은 필수입력입니다.")
    private String user_name;          //'회원 이름'

    @NotEmpty(message = "아이디는 필수입력입니다.")
    private String user_id;            //'회원 아이디'

    @NotNull(message = "생년월일은 필수입력입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @NotEmpty(message = "성별은 필수선택입니다.")
    private String gender;             //'성별'

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;              //'회원 이메일'

    private LocalDateTime joinDate;    //'가입일'

   @NotEmpty(message = "비밀번호를 입력해주세요.")
   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
          message = "비밀번호는 최소 8자리 이상 숫자, 문자, 특수문자 각각 1개 이상 포함 되어야 합니다.")
   private String pwd1; //'비밀번호'

   @NotEmpty(message = "비밀번호확인은 필수입력입니다.")
   private String pwd2;                //'비밀번호'

}
