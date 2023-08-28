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
public class MymapDTO {
    private int map_no;
    private int hotplace_no;
    private int user_no;
    private String recom;
    private String map_memo;
    private Date recom_date;
}
