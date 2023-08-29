package com.yumpro.ddogo.emoAnal.validation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {

    @NotEmpty(message = "리뷰는 필수입력입니다.")
    private String review;

    @NotNull
    private int hotplace_no;

    @NotNull
    private int map_no;


}
