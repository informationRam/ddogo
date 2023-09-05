package com.yumpro.ddogo.qna.domain;

import java.time.LocalDateTime;

public class QnaListDTO {

    private Integer qnaNo;
    private String qnaTitle;
    private char qnaSolved;
    private LocalDateTime qnaDate;
    private String userName;

    public QnaListDTO(Integer qnaNo, String qnaTitle, char qnaSolved, LocalDateTime qnaDate, String userName) {
        this.qnaNo = qnaNo;
        this.qnaTitle = qnaTitle;
        this.qnaSolved = qnaSolved;
        this.qnaDate = qnaDate;
        this.userName = userName;
    }

    // getters and setters

}

