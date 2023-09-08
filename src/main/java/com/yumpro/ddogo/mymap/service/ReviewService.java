package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import com.yumpro.ddogo.mymap.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final MyMapMapper myMapMapper;

    @Transactional
    public void updateReview(EmoReviewDTO emoReviewDTO) {
        // EmoReviewDTO 객체에서 필요한 데이터를 선택 사용
//        Integer mapNo = emoReviewDTO.getMapNo();
//        String recomm = String.valueOf(emoReviewDTO.getRecomm());
//        String memo = emoReviewDTO.getMemo();
//        String review = emoReviewDTO.getReview();

        //moReviewDTO 객체 전체 매퍼에 전달
        reviewMapper.updateReview(emoReviewDTO);
    }

    // mapNo를 기반으로 후기 정보 조회
    public EmoReviewDTO getReviewByMapNo(Integer mapNo) {

        return reviewMapper.getReviewByMapNo(mapNo);
    }
}


