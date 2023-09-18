package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.emoAnal.repository.EmoRepository;
import com.yumpro.ddogo.mymap.domain.ReviewDTO;
import com.yumpro.ddogo.mymap.domain.ReviewUpdateDTO;
import com.yumpro.ddogo.mymap.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final EmoRepository emoRepository;


    // 후기 수정
    public void updateReview(ReviewUpdateDTO request) {
        reviewMapper.updateReview(request); // ReviewMapper를 사용하여 SQL 쿼리 실행
    }


    // mapNo를 기반으로 후기 정보 조회
    public ReviewDTO getReviewByMapNo(Integer mapNo) {

        return reviewMapper.getReviewByMapNo(mapNo);
    }
}


