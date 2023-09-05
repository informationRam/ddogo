package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna,Integer> {

    @Query("SELECT new com.yumpro.ddogo.common.dto.QnaListDto(q.qnaNo, q.qnaTitle, q.qnaSolved, q.qnaDate, u.userName) " +
            "FROM Qna q " +
            "INNER JOIN q.user u " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR q.qnaTitle LIKE %:keyword% OR q.qnaContent LIKE %:keyword% OR CAST(q.qnaSolved AS string) LIKE %:keyword% OR u.userName LIKE %:keyword%)")
    List<QnaListDTO> findQnaList(@Param("keyword") String keyword);

}
