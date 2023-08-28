package com.yumpro.ddogo.admin.repository;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository{
    @Override
    public int getUserTotal(){

        return 1;
    }
}
