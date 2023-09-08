package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.common.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaJpaRepository extends JpaRepository<Qna,Integer> {
}
