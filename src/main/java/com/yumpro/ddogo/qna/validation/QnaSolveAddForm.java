package com.yumpro.ddogo.qna.validation;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import com.yumpro.ddogo.common.entity.Qna;

@Getter
@Setter
@Component
public class QnaSolveAddForm {
    @Size(min = 1, max = 100, message = "답변 제목은 필수입력 항목입니다")
    private String qnaSolveTitle;

    @Size(min = 1, max = 1000, message = "답변 내용을 필수입력 항목입니다")
    private String qnaSolveContent;
}