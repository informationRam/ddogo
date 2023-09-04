package com.yumpro.ddogo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HOTPLACE")
public class Hotplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotplace_no")
    private Integer hotplaceNo;

    @Column(name = "sido")
    private String sido;

    @Column(name = "gugun")
    private String gugun;

    @Column(name = "hotplace_name")
    private String hotplaceName;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계
    @JoinColumn(name = "hotplace_cate_no", referencedColumnName = "hotplace_cate_no") // 외래 키 설정
    private HotplaceCate hotplaceCate;
}
