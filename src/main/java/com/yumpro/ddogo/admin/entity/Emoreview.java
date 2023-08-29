package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;

public class Emoreview {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int review_no;
    @OneToOne
    private int map_no;
    @ManyToOne
    private int hotplace_no;
    @Column
    private String review;
    @Column
    private double emo_result;
}
