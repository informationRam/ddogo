package com.yumpro.ddogo.qna.service;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaService {
    public final QnaRepository qnaRepository;
    public List<QnaListDTO> getQnaList(Map<String,Object> map) {
        return qnaRepository.findQnaList(map);
    }

    public int getQnaListCount(Map<String, Object> map) {
        return qnaRepository.getQnaListCount(map);
    }
}
