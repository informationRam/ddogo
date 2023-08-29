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
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_no;

    @Column
    private String user_id;

    @Column
    private Date BIRTH;

    @Column
    private String gender;

    @Column
    private Date join_date;

    @Column
    private String email;

    @Column
    private String pwd;

    @Column(name="isShow")
    private String isShow;
}