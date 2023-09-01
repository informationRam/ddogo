package com.yumpro.ddogo.mymap.entity;

import com.yumpro.ddogo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "MYMAP")
public class MyMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_no")
    private Integer mapNo; // 열 이름과 변수 이름을 일치시킵니다.

    @OneToOne(mappedBy = "myMap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmoReview emoReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", referencedColumnName = "user_no")
    private User userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotplace_no")
    private Hotplace hotplaceNo;

    @Column(name = "recom")
    private char recom;

    @Column(name = "map_memo")
    private String mapMemo;

    @Column(name = "recom_date")
    private LocalDateTime recomDate;

 }
