package com.yumpro.ddogo.searchmap.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class Mapper {
    @Autowired
    private SqlSession sqlSession;

    public Integer hasHistory(Map<String, Object> map) {
        return sqlSession.selectOne("searchmap.findHistory", map);
    }
}
