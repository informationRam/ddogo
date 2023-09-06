package com.yumpro.ddogo.searchmap.mapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class Mapper {
    @Autowired
    private SqlSession sqlSession;

    public Integer findByUserNoAndHotplaceNo(Map<String, Object> map) {

        return sqlSession.selectOne("searchmap.findByUserNoAndHotplaceNo", map);
    }
}
