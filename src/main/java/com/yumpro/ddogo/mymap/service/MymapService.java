package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MymapService {


    private final MyMapMapper myMapMapper;




    // 사용자 번호와 검색어를 이용하여 맛집 목록 조회
    public List<MyMapDTO> getHotplacesByUserNoWithSearch(int userNo, String search, int pageSize, int offset) {
        // 사용자 번호와 검색어를 이용하여 맛집 목록 조회
       return myMapMapper.getHotplacesByUserNoWithSearch(userNo, search, pageSize, offset);
    }

    //회원별 저장한 맛집 리스트 조회 - mapper
    public List<MyMapDTO> getHotplacesByUserNo(int userNo) {
        return myMapMapper.getHotplacesByUserNo(userNo);
    }

    // 저장한 맛집(마커) 삭제
    public void deleteHotpl(Integer mapNo) {
        try {
            myMapMapper.deleteMyHotpl(mapNo);

        } catch (Exception e) {
            // RuntimeException으로 예외 던지기
            throw new RuntimeException("맛집 삭제 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }
}

