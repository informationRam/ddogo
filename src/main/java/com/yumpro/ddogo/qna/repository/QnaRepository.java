package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.common.entity.Qna;

import com.yumpro.ddogo.qna.domain.QnaListDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;


public interface QnaRepository {
    List<QnaListDTO> findQnaList(Map<String,Object> map) throws DataAccessException;

    int getQnaListCount(Map<String, Object> map);
}
