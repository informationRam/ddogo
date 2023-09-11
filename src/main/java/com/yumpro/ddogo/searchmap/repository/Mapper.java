package com.yumpro.ddogo.searchmap.repository;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class Mapper {
    @Autowired
    private SqlSession sqlSession;
    /*@Select("SELECT map_no FROM mymap WHRER user_no=#{user_no} and hotplace_no=#{hotplace_no}")
    public Integer findByUserNoAndHotplaceNo(Map<String, Object> map){

    }*/
    public Integer hasHistory(Map<String, Object> map) {
        return sqlSession.selectOne("searchmap.findHistory", map);
    }
}
