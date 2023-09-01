package com.yumpro.ddogo.admin.entity;

public class Qna {
    CREATE TABLE `qna` (
            `qna_no` int NOT NULL AUTO_INCREMENT COMMENT '문의 번호. 문의 글 번호',
            `qna_title` varchar(100) NOT NULL COMMENT '문의 제목',
            `qna_content` text NOT NULL COMMENT '문의 내용',
            `user_no` int NOT NULL COMMENT '회원 번호',
            `qa_solved` char(1) NOT NULL DEFAULT 'N' COMMENT '문의 확인여부',
            `qna_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '문의 일',
            `qna_pwd` varchar(50) NOT NULL COMMENT '문의 비밀번호',

    private int qna_no;
}
