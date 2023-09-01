package com.yumpro.ddogo.main.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BestJjimDTO {

    private int review_no;
    private String review;
    private int hotplace_no;
    private int map_no;
    private String hotplace_name;
    private double emo_result;
    private double avg_emo_result;
}
