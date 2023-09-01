package com.yumpro.ddogo.mymap.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMOREVIEW")
public class EmoReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Integer reviewNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_no", referencedColumnName = "map_no")
    private MyMap myMap; //mymap과 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotplace_no", referencedColumnName = "hotplace_no")
    private Hotplace hotplace;

    @Column(name = "review")
    private String review;

    @Column(name = "emo_result")
    private Double emoResult;

}
