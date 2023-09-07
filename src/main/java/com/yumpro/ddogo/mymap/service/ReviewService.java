package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import com.yumpro.ddogo.mymap.mapper.EmoReviewMapper;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final EmoReviewMapper emoReviewMapper;
    private final MyMapMapper myMapMapper;

    @Transactional
    public void updateReview(Map<String, Object> updateInfo) {
        String recomm = (String) updateInfo.get("recomm");
        String memo = (String) updateInfo.get("memo");
        String review = (String) updateInfo.get("review");
        Integer mapNo = (Integer) updateInfo.get("mapNo");
        Integer reviewNo = (Integer) updateInfo.get("reviewNo");

        myMapMapper.updateReview(mapNo, recomm, memo);
        emoReviewMapper.updateEmoReview(reviewNo, review);
    }
    // mapNo를 기반으로 후기 정보 조회
    public EmoReviewDTO getReviewByMapNo(Integer mapNo) {

        return emoReviewMapper.getReviewByMapNo(mapNo);
    }
}

