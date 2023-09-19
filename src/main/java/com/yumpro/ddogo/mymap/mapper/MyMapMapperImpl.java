package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Repository
public class MyMapMapperImpl implements MyMapMapper {
    private final SqlSession sqlSession;



    //검색결과 조회
    @Override
    public List<MyMapDTO> getHotplacesByUserNoWithSearch(int userNo, String search, int pageSize, int offset) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("search", search);
        params.put("pageSize", pageSize);
        params.put("offset", offset);
        return sqlSession.selectList("com.yumpro.ddogo.mymap.mapper.MyMapMapperImpl.getHotplacesByUserNoWithSearch", params);
    }

    //맛집목록조회
    @Override
    public List<MyMapDTO> getHotplacesByUserNo(int userNo) {
        return sqlSession.selectList("com.yumpro.ddogo.mymap.mapper.MyMapMapperImpl.getHotplacesByUserNo", userNo);
    }

    //맛집 목록 삭제
    @Override
    public void deleteMyHotpl(Integer mapNo) {

    }
}