package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ReviewMapper {

    // 수정 쿼리를 호출하는 메서드
   // void updateEmoReview(@Param("reviewNo") Integer reviewNo, @Param("review") String review);
    void updateReview(EmoReviewDTO emoReviewDTO);

    // mapNo를 기반으로 후기 정보 조회
    EmoReviewDTO getReviewByMapNo(@Param("mapNo") Integer mapNo);

}

