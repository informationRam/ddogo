package com.yumpro.ddogo.main.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MainRepositoryImpl implements MainRepository{
    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<HashMap<String, Object>> eatjjim(){
        return sqlSession.selectList("main.allBestEatJjim");
    }

    @Override
    public List<HashMap<String, Object>> cafejjim(){
        return sqlSession.selectList("main.allBestCafeJjim");
    }


}
