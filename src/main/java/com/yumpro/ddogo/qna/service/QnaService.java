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

@Service
@RequiredArgsConstructor
public class QnaService {
    public QnaRepository qnaRepository;
    public List<QnaListDTO> getQnaList(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        return qnaRepository.findQnaList(keyword);
    }
}
