package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "QNA")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qna_no")
    private Integer qnaNo;

    @Column(name="qna_title")
    private String qnaTitle;

    @Column(name="qna_content")
    private String qnaContent;

    //user_no
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_no", referencedColumnName = "user_no")
    private User user;

    @Column(name="qna_solved")
    private char qnaSolved;

    @Column(name="qna_date")
    private LocalDateTime qnaDate;

    @Column(name="qna_pwd")
    private String qnaPwd;
}
