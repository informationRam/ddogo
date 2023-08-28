package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
public class Hotplace {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int hotplace_no;

    @Column
    private String sido;

    @Column
    private String gugun;

    @Column
    private String hotplace_name;

    @Column
    private String address;

    @ManyToOne
    private int hotplace_cate_no;
}

