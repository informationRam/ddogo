package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "QNA_SOLVE")
public class QnaSolve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_solve_no")
    private Integer qnaSolveNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_no", referencedColumnName = "qna_no")
    private Qna qna;

    @Column(name = "qna_solve_title")
    private String qnaSolveTitle;

    @Column(name = "qna_solve_content")
    private String qnaSolveContent;

    @Column(name = "qna_solve_date")
    private LocalDateTime qnaSolveDate;

}
