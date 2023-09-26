package com.yumpro.ddogo.notice.controller;

import com.yumpro.ddogo.common.entity.Notice;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.notice.service.NoticeService;
import com.yumpro.ddogo.notice.validation.Noticeform;
import com.yumpro.ddogo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    // 공지사항 리스트
    @GetMapping("/list")
    public String noticeList(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Noticeform noticeform) {
        Page<Notice> noticePage = this.noticeService.getList(page);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole=null;
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            userRole="admin";
        }else{
            userRole="user";
        }
        //3.Model
        model.addAttribute("userRole",userRole);
        model.addAttribute("noticeform", noticeform);
        model.addAttribute("noticePage", noticePage);
        return "notice/noticeList_Form";
    }

    // 공지사항 상세페이지
    @GetMapping("/detail/{notiNo}")
    public String noticeDetail(@PathVariable Integer notiNo, Model model) {
        System.out.println("notiNo:" + notiNo);
        Notice notice = noticeService.getNotice(notiNo);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole=null;
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            userRole="admin";
        }else{
            userRole="user";
        }
        //3.Model
        model.addAttribute("userRole",userRole);
        model.addAttribute("notice", notice);
        return "notice/noticeDetail_Form";
    }

    // 공지사항 작성 form
    @PreAuthorize("isAuthenticated()") //인증을 요청
    @GetMapping("/add")
    public String noticeaddForm(Noticeform noticeform,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole=null;
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            userRole="admin";
        }else{
            userRole="user";
        }
        //3.Model
        model.addAttribute("userRole",userRole);
        return "notice/noticeadd_Form";
    }

    // 공지사항 작성 (add)
    @PostMapping("/add")
    public String noticeadd(@Valid Noticeform noticeform, BindingResult bindingResult, Principal principal, Model model) {

        model.addAttribute("noticeform", noticeform);
        User user = userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {          //유효성검사시 에러가 발생하면
            return "notice/noticeadd_Form";    //noticeadd_Form.html문서로 이동
        }

        if (!user.getUserId().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "등록권한이 없습니다.");
        }
        noticeService.add(noticeform.getNotiTitle(), noticeform.getNotiContent());
        return "redirect:/notice/list";   // 공지사항 리스트로 이동
    }

    //공지사항 수정폼
    @GetMapping("/modify/{notiNo}")
    public String noticeModifyForm(@PathVariable Integer notiNo, Model model) {
        Notice notice = noticeService.getNotice(notiNo);
        Noticeform noticeform = noticeService.toNoticeform(notice);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole=null;
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            userRole="admin";
        }else{
            userRole="user";
        }
        //3.Model
        model.addAttribute("userRole",userRole);
        model.addAttribute("noticeform", noticeform);
        model.addAttribute("notiNo", notiNo);
        return "notice/noticeModify_Form";
    }

    //공지사항 수정등록
    @PostMapping("/modify/{notiNo}")
    public String noticeModify(@PathVariable Integer notiNo, @Valid Noticeform noticeform, BindingResult bindingResult, Model model) {
        model.addAttribute("noticeform", noticeform);

        if (bindingResult.hasErrors()) {
            System.out.println("noticeform-getNotiContent:" + noticeform.getNotiContent());
            System.out.println("noticeform-getNotiTitle:" + noticeform.getNotiTitle());
            // 유효성 검사 실패 시 에러 처리
            return "notice/noticeModify_Form"; // 수정페이지 유지
        }
        Notice notice = noticeService.getNotice(notiNo);
        noticeService.modify(notice, noticeform);
        System.out.println("수정 ㅇㅋ");
        return "redirect:notice/detail/" + notiNo; // 수정 후 공지사항 상세페이지로 리다이렉트
    }
    // 공지사항 삭제처리
    @GetMapping("/delete/{notiNo}")
    public String noticeDelete(@PathVariable("notiNo") Integer notiNo, Principal principal) {
        Notice notice = noticeService.getNotice(notiNo);    // notice 상세

        // 현재 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 현재 사용자의 권한 중 하나라도 "ROLE_ADMIN"인 경우
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            noticeService.noticeDelete(notice);
            return "redirect:/notice/list";    // 공지사항 목록으로 이동
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
    }
}