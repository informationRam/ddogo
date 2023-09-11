package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.qna.domain.QnaListDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;


public interface QnaRepository {
    List<QnaListDTO> findQnaList(Map<String,Object> map) throws DataAccessException;

    int getQnaListCount(Map<String, Object> map);
}
