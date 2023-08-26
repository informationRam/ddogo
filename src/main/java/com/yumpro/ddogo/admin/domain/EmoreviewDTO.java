package com.yumpro.ddogo.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class EmoreviewDTO {
    private int review_no;
    private int map_no;
    private int hotplace_no;
    private String review;
    private double emo_result;
}
