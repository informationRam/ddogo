package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "NOTICE")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noti_no")
    private Integer notiNo;

    @Column(name="noti_title")
    private String notiTitle;

    @Column(name="noti_content")
    private String notiContent;

    @Column(name="noti_Date")
    private LocalDateTime notiDate;



}
