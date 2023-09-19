package com.yumpro.ddogo.mymap.mapper;

import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MyMapMapper {

    //검색, 페이지네이션
    List<MyMapDTO> getHotplacesByUserNo(@Param("userNo") Integer userNo,
                                        @Param("search") String search,
                                        @Param("pageSize") int pageSize,
                                        @Param("offset") int offset);

    //검색어가 있을때 총 게시글 개수
    int countHotplacesByUserNoWithSearch(
            @Param("userNo") Integer userNo,
            @Param("search") String search
    );

    //검색어가 있을때 총 게시글 개수
    int countHotplacesByUserNo(@Param("userNo") Integer userNo);

//    //회원별 맛집 목록 불러오기 :=>  mymap.xml에서 쿼리 작성
//   List<MyMapDTO> getHotplacesByUserNo(@Param("userNo") int userNo);


    //맛집 삭제
    void deleteMyHotpl(@Param("mapNo") Integer mapNo);



}
