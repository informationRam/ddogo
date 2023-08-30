package com.yumpro.ddogo.admin.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class ImsiRepository {
    //필드
    @Autowired
    private SqlSession sqlSession;

    //생성자
    //메서드
    //회원수조회
    public int selectTotalMemberCNT() throws DataAccessException {
        int totalMemberCNT = (Integer) sqlSession.selectOne("admin.mapno");
        return totalMemberCNT;
    }
}
