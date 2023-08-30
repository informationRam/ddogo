package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;

public class Hotplace {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int hotplace_no;
    @ManyToOne??
    String sido;
    @Column
    String gugun;
    @Column
    String hotplace_name;
    String address;
    @Column
    int hotplace_cate_no;
    @Column
    double lat;
    @Column
    double lng;
}
