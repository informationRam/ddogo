package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
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