package com.yumpro.ddogo.qna.service;

import com.yumpro.ddogo.admin.exception.DataNotFoundException;
import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.common.entity.QnaSolve;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.repository.QnaJpaRepository;
import com.yumpro.ddogo.qna.repository.QnaRepository;
import com.yumpro.ddogo.qna.repository.QnaSolveRepository;
import com.yumpro.ddogo.qna.validation.QnaSolveAddForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    public final QnaRepository qnaRepository;
    public final QnaJpaRepository qnaJpaRepository;
    public final QnaSolveRepository qnaSolveRepository;

    public List<QnaListDTO> getQnaList(Map<String,Object> map) {
        return qnaRepository.findQnaList(map);
    }

    public int getQnaListCount(Map<String, Object> map) {
        return qnaRepository.getQnaListCount(map);
    }

    public void add(String qnaTitle, String qnaContent, String qnaPwd, User user) {
        Qna qna = new Qna();
        qna.setQnaTitle(qnaTitle);
        qna.setQnaContent(qnaContent);
        qna.setQnaPwd(qnaPwd);
        qna.setQnaDate(LocalDateTime.now());
        qna.setQnaSolved('N');
        qna.setUser(user);
        qnaJpaRepository.save(qna);
    }

    public Qna getQnaById(Integer id){
        Optional<Qna> optionalQna=qnaJpaRepository.findById(id);

        if(optionalQna.isPresent()){
            return optionalQna.get();
        } else{
            throw new DataNotFoundException();
        }
    }

    public QnaSolve getQnaSolveByQna(Qna qna){
        Optional<QnaSolve> optionalQnaSolve =qnaSolveRepository.findByQna(qna);

        if(optionalQnaSolve.isPresent()){
            return optionalQnaSolve.get();
        }else {
            return null;
        }
    }

    public void Solveadd(String qnaSolveTitle,String qnaSolveContent,Qna qna) {
        QnaSolve qnaSolve = new QnaSolve();
        qnaSolve.setQnaSolveTitle(qnaSolveTitle);
        qnaSolve.setQnaSolveContent(qnaSolveContent);
        qnaSolve.setQna(qna);
        qnaSolve.setQnaSolveDate(LocalDateTime.now());
        qnaSolveRepository.save(qnaSolve);
        qna.setQnaSolved('Y');
        qnaJpaRepository.save(qna);
    }

    public QnaSolve getQnaSolveById(int id) {
        Optional<QnaSolve> optionalQnaSolve = qnaSolveRepository.findById(id);

        if(optionalQnaSolve.isPresent()){
            return optionalQnaSolve.get();
        }else{
            throw new DataNotFoundException();
        }
    }

    public void moidfy(QnaSolve qnaSolve, String title, String content) {
        qnaSolve.setQnaSolveTitle(title);
        qnaSolve.setQnaSolveContent(content);
        qnaSolve.setQnaSolveDate(LocalDateTime.now());
        qnaSolveRepository.save(qnaSolve);
    }

    public void delete(QnaSolve qnaSolve) {
        qnaSolveRepository.delete(qnaSolve);
    }
}
