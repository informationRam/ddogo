package com.yumpro.ddogo.searchmap.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchMapDTO {
    //필드
    private double lng; //경도
    private double lat; //위도
    private String placeName; //상호명
    private String address; //전체 도로명 주소
    private String sido; //시도
    private String gugun; //시군구
    //생성자

    //메소드
}
