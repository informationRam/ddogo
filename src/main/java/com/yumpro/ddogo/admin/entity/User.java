package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
public class User {
    @Id//대소문자 주의
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_no;
    @Column
    private String user_name;
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
}
