package com.yumpro.ddogo.qna.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@RequiredArgsConstructor
public class QnaListDTO {
    private int qna_no;
    private String qna_title;
    private int user_no;
    private String user_id;
    private char qna_solved;
    private LocalDateTime qna_date;
    private String qna_pwd;
}


