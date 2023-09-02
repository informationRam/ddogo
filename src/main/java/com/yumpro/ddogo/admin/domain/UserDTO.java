package com.yumpro.ddogo.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class UserDTO {
    private int user_no;
    private String user_name;
    private String user_id;
    private Date BIRTH;
    private String gender;
    private Date join_date;
    private String email;
    private String pwd;
}
