package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface EmoReviewMapper {

    //후기 가져오기
    EmoReviewDTO getReviewById(@Param("reviewNo")Integer reviewNo);
    //후기 수정
    void updateReview(@Param("reviewNo")Integer reviewNo,@Param("review") String review);
    // mapNo를 기반으로 후기 정보 조회
    EmoReviewDTO getReviewByMapNo(@Param("mapNo") Integer mapNo);
}
