package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
public class Emoreview {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int review_no;

    @Column
    private int map_no;

    @Column
    private int hotplace_no;

    @Column
    private String review;

    @Column
    private double emo_result;
}
