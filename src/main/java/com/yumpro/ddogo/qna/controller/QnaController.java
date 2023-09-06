package com.yumpro.ddogo.qna.controller;

import com.yumpro.ddogo.common.entity.Qna;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.qna.domain.QnaListDTO;
import com.yumpro.ddogo.qna.service.QnaService;
import com.yumpro.ddogo.admin.service.UserListService;
import com.yumpro.ddogo.qna.validation.QnaAddForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;
    private final UserListService userListService;

    //(all)문의글 리스트 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String qnaList(Model model, @RequestParam Map<String, Object> map, @RequestParam(value = "page", defaultValue = "1") int currentPage, Principal principal) {

        int limit = 15; // 페이지당 보여줄 아이템 개수
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
        model.addAttribute("userId",principal.getName());

        return "qna/qna_list";
    }

    //(유저)문의글 작성 폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String qnaAdd(QnaAddForm qnaAddForm){
        return "qna/qna_add";
    }

    //(유저)문의글 작성 처리
    @PreAuthorize("isAuthenticated()")//로그인인증=>로그인이 필요한 기능
    @PostMapping("/add")
    public String qnaAdd(@Valid QnaAddForm qnaAddForm, BindingResult bindingResult,
                         Principal principal) {
        //1.파라미터받기 qna_title,qna_content,qna_pwd
        //2.비즈니스로직
        if(bindingResult.hasErrors()) { //에러가 존재하면
            return "qna_add";
        }

        Optional<User> user = userListService.findUserByUserId(principal.getName());//user정보가져오기

        qnaService.add(qnaAddForm.getQna_title(),qnaAddForm.getQna_content(),qnaAddForm.getQna_pwd(),user.get());

        //3.Model&4.View
        return "redirect:/qna/list"; //(질문목록조회요청을 통한)질문목록페이지로 이동
    }

    //문의글 상세 보기 (비밀번호 확인 / 관리자는 free)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/detail/{id}")
    public String qnaDetail(@PathVariable int id, @RequestParam String inputPwd, Model model, Principal principal){

        Qna qna = qnaService.getQnaById(id);

        if(!principal.getName().equals("admin")){
            if(!qna.getQnaPwd().equals(inputPwd)){
                return "redirect:/qna/list";
            }
        }
        model.addAttribute("qna",qna);
        return "qna/qna_detail";
    }

    //(관리자)문의글 답변 달기

    //(관리자)문의글 답변 수정

    //(관리자)문의글 답변 삭제
}

