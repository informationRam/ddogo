package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.emoAnal.repository.EmoRepository;
import com.yumpro.ddogo.mymap.domain.ReviewDTO;
import com.yumpro.ddogo.mymap.domain.ReviewUpdateDTO;
import com.yumpro.ddogo.mymap.mapper.ReviewMapper;
import com.yumpro.ddogo.mymap.repository.MyMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final MyMapRepository myMapRepository;
    private final EmoRepository emoRepository;
    // 후기 업데이트 및 감정 분석 결과 저장
/*
    public void updateReviewandMemo(Integer mapNo, ReviewUpdateDTO request, double emoResult) {
        // mapNo를 사용하여 mymap 테이블의 memo, recomm_date, recomm 업데이트
        // request에서 memo, recomm 값을 추출하여 업데이트
        MyMap myMap = myMapRepository.findById(mapNo).orElse(null);
        if (myMap != null) {
            myMap.setMapMemo(request.getMemo());
            myMap.setRecom(request.getRecomm());
            myMap.setRecomDate(LocalDateTime.now());
             myMapRepository.save(myMap);

        } else {
            //  mapNo에 해당하는 레코드가 없을 경우 예외 처리 또는 적절한 동작 수행
            System.out.println("수정할 맛집 정보가 없습니다");
        }
            // 새로운 후기 업데이트
            // request에서 review 값을 추출하여 업데이트
            Emoreview emoreview = new Emoreview();
            emoreview.setReview(request.getReview());
            emoreview.setHotplace_no(request.getHotplaceNo());
            emoreview.setMap_no(mapNo);
            emoreview.setEmo_result(emoResult);
            emoRepository.save(emoreview);
        }
*/

    // 후기 수정 :mybatis버전

    public void updateReview(ReviewUpdateDTO request) {
        reviewMapper.updateReview(request); // ReviewMapper를 사용하여 SQL 쿼리 실행
    }


    // mapNo를 기반으로 후기 정보 조회
    public ReviewDTO getReviewByMapNo(Integer mapNo) {

        return reviewMapper.getReviewByMapNo(mapNo);
    }
}


