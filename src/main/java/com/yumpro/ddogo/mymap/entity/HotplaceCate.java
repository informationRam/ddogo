package com.yumpro.ddogo.mymap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HOTPLACE_CATE")
public class HotplaceCate {

    @Id
    @Column(name = "hotplace_cate_no")
    private Integer hotplaceCateNo;

    @Column(name = "hotplace_cate_name")
    private String hotplaceCateName;


}