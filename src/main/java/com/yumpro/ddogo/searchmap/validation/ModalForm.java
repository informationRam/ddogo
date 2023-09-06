package com.yumpro.ddogo.searchmap.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 유효성검사용 클래스
@Getter
@Setter
public class ModalForm {

    @NotBlank(message = "리뷰란은 필수입력입니다.")
    @Size(max=1500) //최대 500자
    private String inputReview;

    @Size(max=900) //최대 300자
    private  String inputMemo;

    @NotNull(message = "또갈지도의 여부를 선택해주세요.")
    private char myRecommend;
    //private MyRecommend myRecommend; //추천여부


}
