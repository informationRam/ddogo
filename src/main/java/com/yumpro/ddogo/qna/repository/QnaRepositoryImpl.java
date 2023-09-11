package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.qna.repository.QnaRepository;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class QnaRepositoryImpl implements QnaRepository {
    private final SqlSession sqlSession;
    @Override
    public List<QnaListDTO> findQnaList(Map<String,Object> map) throws DataAccessException{
        return sqlSession.selectList("qna.qnaList",map);
    }

    @Override
    public int getQnaListCount(Map<String, Object> map){
        return sqlSession.selectOne("qna.qnaListCnt",map);
    }
}
