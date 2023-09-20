package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MymapService {


    private final MyMapMapper myMapMapper;


    //회원 맛집 정보 조회 & 검색 & 페이지네이션
    public List<MyMapDTO> getHotplacesWithSearch(int userNo, String search, int page, int pageSize) {
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("userNo", userNo);
        searchParams.put("search", search);
        searchParams.put("offset", page * pageSize);
        searchParams.put("pageSize", pageSize);
        return myMapMapper.getHotplacesWithSearch(searchParams);
    }

    //회원 맛집 정보 총 갯수
    public int getTotalCountWithSearch(int userNo, String search) {
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("userNo", userNo);
        searchParams.put("search", search);
        return myMapMapper.getTotalCountWithSearch(searchParams);
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





//    // 변경된 메서드명을 사용하는 서비스 메서드
//    public List<MyMapDTO> getHotplaces(@Param("userNo") Integer userNo,
//                                       @Param("search") String search,
//                                       @Param("pageSize") int pageSize,
//                                       @Param("offset") int offset){
//
//        System.out.println("서비스userNo: " + userNo);
//        // 검색어가 빈 문자열일 경우 null로 변경
//        if ("".equals(search)) {
//            search = null;
//        }
//        return myMapMapper.getHotplaces(userNo, search, pageSize, offset);
//    }


//    // 사용자 번호와 검색어를 이용하여 맛집 목록 조회
//    public List<MyMapDTO> getHotplacesByUserNoWithSearch(Integer userNo, String search, int pageSize, int offset) {
//        // 사용자 번호와 검색어를 이용하여 맛집 목록 조회
//       return myMapMapper.getHotplacesByUserNoWithSearch(userNo, search, pageSize, offset);
//
//    }
//
//    //회원별 저장한 맛집 리스트 조회 - mapper
//    public List<MyMapDTO> getHotplacesByUserNo(Integer userNo) {
//        System.out.println("userNo: " + userNo);
//        return myMapMapper.getHotplacesByUserNo(userNo);
//
//    }



