package com.yumpro.ddogo.notice.controller;


import com.yumpro.ddogo.common.entity.Notice;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.notice.service.NoticeService;
import com.yumpro.ddogo.notice.validation.Noticeform;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;
    // 공지사항 리스트
    @GetMapping("/list")
    public String noticeList(Model model,@RequestParam(value = "page",defaultValue = "0")int page,Noticeform noticeform) {
        Page<Notice> noticePage = this.noticeService.getList(page);
        model.addAttribute("noticeform",noticeform);
        model.addAttribute("noticePage",noticePage);
        return "/notice/noticeList_Form";
    }



    // 공지사항 상세페이지





    // 공지사항 작성 form
    @PreAuthorize("isAuthenticated()") //인증을 요청
    @GetMapping("/add")
    public String noticeaddForm(Noticeform noticeform){
        return "/notice/noticeadd_Form";
    }

    // 공지사항 작성 (add)
    @PostMapping("/add")
    public String noticeadd(@Valid Noticeform noticeform, BindingResult bindingResult, Principal principal,Model model){

        model.addAttribute("noticeform",noticeform);
        User user = userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){          //유효성검사시 에러가 발생하면
            return "/notice/noticeadd_Form";    //noticeadd_Form.html문서로 이동
        }

        if ( !user.getUserId().equals("admin") ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"등록권한이 없습니다.");
        }noticeService.add(noticeform.getNotiTitle(), noticeform.getNotiContent());
        return "redirect:/notice/list";   // 공지사항 리스트로 이동
    }






}//class