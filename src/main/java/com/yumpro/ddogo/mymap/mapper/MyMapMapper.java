package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MyMapMapper {

    //회원별 맛집 목록 불러오기 :=>  mymap.xml에서 쿼리 작성
   List<MyMapDTO> hotplacesByUserNo(@Param("userNo") int userNo);


    //맛집 저장 삭제
    void deleteMyHotpl(Integer mapNo);
}
