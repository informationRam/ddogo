package com.yumpro.ddogo.qna.validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class QnaAddForm {
    @Size(max = 100)
    @NotEmpty(message = "문의 제목은 필수입력입니다.")
    private String qna_title;

    @NotEmpty(message = "문의 내용은 필수입력입니다.")
    private String qna_content;

    @Size(max = 50)
    @NotEmpty(message = "문의 비밀번호는 필수입력입니다.")
    private String qna_pwd;
}
