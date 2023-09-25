package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MymapService {


    private final MyMapMapper myMapMapper;

    //회원별 저장한 맛집 리스트 조회 - mapper
    public Page<MyMapDTO> getHotplacesByUserNo(int userNo, Pageable pageable) {
        List<MyMapDTO> hotplList = myMapMapper.hotplacesByUserNo(userNo);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), hotplList.size());
        return new PageImpl<>(hotplList.subList(start, end), pageable, hotplList.size());
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

