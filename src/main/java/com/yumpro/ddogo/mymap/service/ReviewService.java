package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import com.yumpro.ddogo.mymap.mapper.EmoReviewMapper;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final EmoReviewMapper emoReviewMapper;
    private final MyMapMapper myMapMapper;

    public EmoReviewDTO getReviewById(Integer reviewNo){
       return emoReviewMapper.getReviewById(reviewNo);

    }
    @Transactional
    public void updateReviewAndMemo(Integer reviewNo, String review, String memo) {
        // 리뷰 업데이트
        emoReviewMapper.updateReview(reviewNo, review);
        // 메모 업데이트
        myMapMapper.updateMemoByReviewNo(reviewNo, memo);
    }
    // mapNo를 기반으로 후기 정보 조회
    public EmoReviewDTO getReviewByMapNo(Integer mapNo) {
        return emoReviewMapper.getReviewByMapNo(mapNo);
    }
}

