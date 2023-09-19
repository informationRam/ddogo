//package com.yumpro.ddogo.mymap.mapper;
//
//import com.yumpro.ddogo.mymap.domain.MyMapDTO;
//import lombok.RequiredArgsConstructor;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Repository
//public class MyMapMapperImpl implements MyMapMapper {
//    private final SqlSession sqlSession;
//
//    //검색결과 조회 및 페이지네이션
//    @Override
//    public Page<MyMapDTO> getHotplacesByUserNo(Integer userNo, String search, Pageable pageable) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("userNo", userNo);
//        params.put("search", search);
//        params.put("pageable", pageable);
//        //mybatis로 SQL쿼리 가져오기
//        List<MyMapDTO> data = sqlSession.selectList("com.yumpro.ddogo.mymap.mapper.MyMapMapper.getHotplacesByUserNo", params);
//
//        // PageImpl 생성 시 제네릭 타입 지정
//        return new PageImpl<>(data, pageable, data.size());
//    }
//
////    //맛집목록조회
////    @Override
////    public List<MyMapDTO> getHotplacesByUserNo(int userNo) {
////        return sqlSession.selectList("com.yumpro.ddogo.mymap.mapper.MyMapMapperImpl.getHotplacesByUserNo", userNo);
////    }
//
//    //맛집 목록 삭제
//    @Override
//    public void deleteMyHotpl(Integer mapNo) {
//
//    }
//}
//
