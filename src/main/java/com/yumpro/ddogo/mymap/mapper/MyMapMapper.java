package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MyMapMapper {

    //ajax페이지별 저장한 맛집 리스트 조회
    List<MyMapDTO> hotplacesByUserNoPaged(@Param("userNo") int userNo, @Param("offset") int offset, @Param("limit") int limit);

    //회원별 맛집 목록 불러오기 :=>  mymap.xml에서 쿼리 작성
   List<MyMapDTO> hotplacesByUserNo(@Param("userNo") int userNo);


    //맛집 삭제
    void deleteMyHotpl(@Param("mapNo") Integer mapNo);



}
