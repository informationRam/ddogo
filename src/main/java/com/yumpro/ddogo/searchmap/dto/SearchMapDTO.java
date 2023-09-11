package com.yumpro.ddogo.searchmap.dto;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class SearchMapDTO {
    //hotplace
    private Integer hotplaceNo; //pk
    private double lng; //경도
    private double lat; //위도
    private String placeName; //상호명
    private int placeCateNo; //카테고리번호(음식점/카페)
    private String address; //전체 도로명 주소
    private String sido; //시도
    private String gugun; //시군구

    //emoreview
    private String inputReview; //리뷰

    //mymap
    private Integer mapNo; //pk
    private char myRecommend; //추천여부
    private String inputMemo; //메모

}
