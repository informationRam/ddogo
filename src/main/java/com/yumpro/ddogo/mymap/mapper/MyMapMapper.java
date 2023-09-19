package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MyMapMapper {

    // 새로운 쿼리를 사용하는 메서드
    List<MyMapDTO> getHotplaces(
            @Param("userNo") Integer userNo,
            @Param("search") String search,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );
//    //검색, 페이지네이션
//    List<MyMapDTO> getHotplacesByUserNoWithSearch(@Param("userNo") Integer userNo,
//                                                  @Param("search") String search,
//                                                  @Param("pageSize") int pageSize,
//                                                  @Param("offset") int offset);
//
//
//    //회원별 맛집 목록 불러오기 :검색어없을때
//   List<MyMapDTO> getHotplacesByUserNo(@Param("userNo") Integer userNo);


    //맛집 삭제
    void deleteMyHotpl(@Param("mapNo") Integer mapNo);



}

