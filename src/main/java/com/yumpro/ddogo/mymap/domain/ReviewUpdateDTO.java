package com.yumpro.ddogo.mymap.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ReviewUpdateDTO {

    private String review;

    private String memo;

    private char recomm;

    private Double emo_result;


    private Integer mapNo;

    private int review_no;

    private int hotplaceNo;

    private Date recom_date;

}
