package com.yumpro.ddogo.qna.repository;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.common.entity.QnaSolve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaSolveRepository extends JpaRepository<QnaSolve,Integer> {
    Optional<QnaSolve> findByQna(Qna qna);
}
