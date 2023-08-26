package com.yumpro.ddogo.admin.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository{
    @Autowired
    private SqlSession sqlSession;
    public int getUserTotal(){
        return  sqlSession.selectOne("statistics.userCnt");
    }
}
