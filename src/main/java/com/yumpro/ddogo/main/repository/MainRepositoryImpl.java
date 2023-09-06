package com.yumpro.ddogo.main.repository;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MainRepositoryImpl implements MainRepository{
    private final SqlSession sqlSession;

    @Override
    public List<HashMap<String, Object>> eatjjim(){
        return sqlSession.selectList("main.allBestEatJjim");
    }

    @Override
    public List<HashMap<String, Object>> cafejjim(){
        return sqlSession.selectList("main.allBestCafeJjim");
    }

    @Override
    public List<String> getSelectList() throws DataAccessException{
        return sqlSession.selectList("main.sido");
    }

    @Override
    public List<String> gugunList(String sido) throws DataAccessException{
        List<String> gugunList = sqlSession.selectList("main.gugunList",sido);
        return gugunList;
    }


    @Override
    public List<HashMap<String, Object>> monthBest(Map<String, Object> paramMap) {
        return sqlSession.selectList("main.monthBest", paramMap);
    }

}
