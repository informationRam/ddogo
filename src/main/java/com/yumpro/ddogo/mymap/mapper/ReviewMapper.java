package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.ReviewDTO;
import com.yumpro.ddogo.mymap.domain.ReviewUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ReviewMapper {

   // 모달 맛집 리뷰 수정
    void updateReview(ReviewUpdateDTO request);

    // mapNo를 기반으로 후기 정보 조회
    ReviewDTO getReviewByMapNo(@Param("mapNo") Integer mapNo);

}

