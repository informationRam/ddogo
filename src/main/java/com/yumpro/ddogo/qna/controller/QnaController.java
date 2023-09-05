package com.yumpro.ddogo.qna.controller;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    //(all)문의글 리스트 보기
    @GetMapping("/list")
    public String qnaList(Model model, @RequestParam Map<String, Object> map, @RequestParam(value = "page", defaultValue = "1") int currentPage) {

        int limit = 1; // 페이지당 보여줄 아이템 개수
        int offset = (currentPage - 1) * limit;

        map.put("limit", limit);
        map.put("offset", offset);

        List<QnaListDTO> qnaList = qnaService.getQnaList(map);

        int totalCount = qnaService.getQnaListCount(map); // 전체 데이터 수를 가져오는 메서드를 추가해야 합니다.
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        model.addAttribute("totalCnt",totalCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("qnaList",qnaList);

        return "qna/qna_list";
    }
}

    //(유저)문의글 작성 폼
    //(유저)문의글 작성 처리
    //문의글 상세 보기 (비밀번호 확인 / 관리자는 free)
    //(관리자)문의글 답변 달기
    //(관리자)문의글 답변 수정
    //(관리자)문의글 답변 삭제