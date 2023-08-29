package com.yumpro.ddogo.emoAnal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emoreview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_no;

    @Column
    private String review;

    @Column
    private int hotplace_no;

    @Column
    private int map_no;

    @Column
    private double emo_result;
}
