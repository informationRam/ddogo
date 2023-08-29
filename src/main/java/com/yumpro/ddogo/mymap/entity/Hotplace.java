package com.yumpro.ddogo.mymap.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Hotplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotplaceNo;

    @Column(name = "sido")
    private String sido;

    @Column(name = "gugun")
    private String gugun;

    @Column(name = "hotplace_name")
    private String hotplaceName;

    @Column(name = "address")
    private String address;

    @Column(name = "hotplace_cate_no")
    private int hotplaceCateNo;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

}
