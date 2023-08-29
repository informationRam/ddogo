package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;

import java.util.Date;

public class Mymap {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int map_no;
    @ManyToOne
    private int hotplace_no;
    @ManyToOne
    private int user_no;
    @Column
    private String recom;
    @Column
    private String map_memo;
    @Column
    private Date recom_date;
}
