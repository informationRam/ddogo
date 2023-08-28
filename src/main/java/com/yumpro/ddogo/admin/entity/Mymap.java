package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Entity
public class Mymap {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int noti_no;

    @Column
    private String noti_title;

    @Column
    private String noti_content;

    @Column
    private Date noti_date;
}
