package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;

public class HotplaceCate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int hotplace_cate_no;
    @Column
    private String hotplace_cate_name;
}
