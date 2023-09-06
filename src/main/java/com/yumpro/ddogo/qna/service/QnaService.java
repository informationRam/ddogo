package com.yumpro.ddogo.qna.service;

import com.yumpro.ddogo.admin.exception.DataNotFoundException;
import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.repository.QnaJpaRepository;
import com.yumpro.ddogo.qna.repository.QnaRepository;
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
}
