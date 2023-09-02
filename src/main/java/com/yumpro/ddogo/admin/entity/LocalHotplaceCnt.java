package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LocalHotplaceCnt")
public class LocalHotplaceCnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "hotplace_cnt", nullable = false)
    private Integer hotplaceCnt;

}
