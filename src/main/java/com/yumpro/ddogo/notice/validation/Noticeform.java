package com.yumpro.ddogo.notice.validation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Noticeform {

    @NotEmpty(message = "제목은 필수입력해야합니다.")
    private String notiTitle;       //공지제목

    @NotEmpty(message = "내용은 필수입력해야합니다.")
    private String notiContent;     //공지내용

    private LocalDateTime notiDate;

}
