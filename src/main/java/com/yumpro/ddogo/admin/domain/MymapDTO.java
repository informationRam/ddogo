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
    private int noti_no;
    private String noti_title;
    private String noti_content;
    private Date noti_date;
}
