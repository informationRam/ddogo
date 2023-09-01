package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
public class Qna {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int qna_no;

    @Column
    private String qna_title;

    @Column
    private String qna_content;

    @ManyToOne
    private int user_no;

    @OneToMany
    private String qa_solved;

    @Column
    private Date qna_date;

    @Column
    private String qna_pwd;
}