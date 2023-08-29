package com.yumpro.ddogo.emoAnal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EmoReviewDTO {

    private int review_no;
    private String review;
    private int hotplace_no;
    private int map_no;
    private double emo_result;
}
