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


    // 검색어가 있는 경우
    public List<MyMapDTO> getHotplacesByUserNo(Integer userNo, String search, int pageSize, int offset) {
        return myMapMapper.getHotplacesByUserNo(userNo, search, pageSize, offset);
    }

    // 검색어가 없는 경우
    public List<MyMapDTO> getHotplacesByUserNo(Integer userNo, int pageSize, int offset) {
        return myMapMapper.getHotplacesByUserNo(userNo, null, pageSize, offset);
    }
    // 검색어가 있는 경우의 총 아이템 수
    public int countHotplacesByUserNoWithSearch(Integer userNo, String search) {
        return myMapMapper.countHotplacesByUserNoWithSearch(userNo, search);
    }

    // 검색어가 없는 경우의 총 아이템 수
    public int countHotplacesByUserNo(Integer userNo) {
        return myMapMapper.countHotplacesByUserNo(userNo);
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

