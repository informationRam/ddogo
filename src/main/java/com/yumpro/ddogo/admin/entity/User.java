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
    private Date birth;

    @Column
    private String gender;

    @Column
    private Date joinDate;

    @Column
    private String email;

    @Column
    private String pwd;
}