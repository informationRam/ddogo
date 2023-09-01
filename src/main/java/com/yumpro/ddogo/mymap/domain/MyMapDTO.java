package com.yumpro.ddogo.mymap.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
// 회원별 저장한 맛집 정보 및 후기 데이터 저장 클래스
public class MyMapDTO {

    //회원별 저장 맛집 리스트 정보를 가져오기 위한 dto
    private int userNo;              // 회원번호
    private Integer mapNo;           // 마커생성번호
    private Integer hotplaceNo;      // 맛집번호
    private String hotplaceName;     // 맛집이름
    private String review;           // 후기
    private char recomm; // 추천유무 ('Y' 또는 'N')
    private String sido;             // 시도
    private String gugun;            // 구군
    private String address;          // 도로명주소
    private Double lat;         // 위도
    private Double lng;        // 경도
    private Double avgEmoResult;     // 맛집별 평균 감정 결과
    private Integer hotplaceCateNo;  // 맛집 카테고리 번호

    //생성자
    //메서드

}
