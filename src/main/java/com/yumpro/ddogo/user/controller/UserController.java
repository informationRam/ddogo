package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.mail.service.EmailService;

import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import com.yumpro.ddogo.user.validation.UserModifyForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

//시큐리티 저장 값 가져오기 세션대용
   /* User user = userService.getUser(principal.getName());
        model.addAttribute("user", user.get().getUser_no());        ==> 유저의 no를 가져옴    */

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 메일발송 서비스
    private final EmailService emailService;
    //세션값 담기(1)
    private final HttpSession session;

    //회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(UserCreateForm userCreateForm, Model model) {
        model.addAttribute("userCreateForm", userCreateForm);
        return "/user/joinForm";
    }

    //회원가입처리 후 로그인페이지로 이동
    @PostMapping("/join")
    public String userJoin(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/user/loginForm";
        }

        // 아이디 중복 여부체크
        if (userService.checkUserIdDuplication(userCreateForm.getUser_id())) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디입니다.");
            return "/user/loginForm";
        }

        // 이메일 중복 여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "/user/loginForm";
        }

        // 비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호 확인 값이 다릅니다.");
            return "/user/loginForm";
        } else {
            userService.userJoin(userCreateForm);
            return "redirect:/user/login";
        }
    }

    //로그인 화면(get)
    @GetMapping("/login")
    public String loginForm(LoginVaildation loginVaildation, Model model, UserCreateForm userCreateForm) {
        model.addAttribute("userCreateForm", userCreateForm);
        return "/user/loginForm";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult, Model model, HttpSession session, Principal principal) {
        System.out.println("?durlf?");
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        } else {
            return "redirect:/";
        }
    }

    //id 찾기 form
    @GetMapping("/searchidform")
    public String searchidform() {
        return "/user/searchid_Form";
    }

    //id 찾기 GET 요청
    @GetMapping("/searchid")
    @ResponseBody
    public Map<String, String> getsearchid(@RequestParam("email") String email) {
        Map<String, String> user = new HashMap<>();
        String userid = userService.searchId(email);
        //회원정보에 사용자가 입력한 이메일이 있는지 확인
        if (!userService.checkEmailDuplication(email) || userid == null) {
            user.put("userid", "회원정보를 찾을 수 없습니다.");
            return user;
        } else {
            user.put("userid", userid);
            return user;
        }
    }

    //id 찾기 POST 요청
    @PostMapping("/searchid")
    @ResponseBody
    public Map<String, String> postsearchid(@RequestParam("email") String email) {
        Map<String, String> user = new HashMap<>();
        String userid = userService.searchId(email);
        //회원정보에 사용자가 입력한 이메일이 있는지 확인
        if (!userService.checkEmailDuplication(email) || userid == null) {
            user.put("userid", "회원정보를 찾을 수 없습니다.");
            return user;
        } else {
            user.put("userid", userid);
            return user;
        }
    }

    // 비밀번호 찾기 폼 pwdsearch_Form
    @GetMapping("/pwdsearchfrom")
    public String pwdsearchForm() {
        return "/user/pwdsearch_Form";
    }

    // 비밀번호 찾기
    @PostMapping("/pwdsearch")
    @ResponseBody
    public Map<String, String> pwdsearch(@RequestParam("email") String email, @RequestParam("user_id") String user_id) {
        System.out.println("email" + email);
        System.out.println("user_id" + user_id);
        Map<String, String> message = new HashMap<>();

        // 이메일 중복 여부 체크
        if (userService.pwdsearch(user_id, email)) {
            message.put("message", "사용자 정보를 찾을 수 없습니다.");
            return message;
        } else {
            String tempPassword = emailService.sendSimpleMessage(email);   // 메일 발송 후 임시 비밀번호 값 저장
            User user = userService.getUser(user_id);
            userService.userpwdModify(user, tempPassword);                // 임시 패스워드로 변경
            message.put("message", "메일을 발송하였습니다.");
            return message; // 메일 전송 성공 -> 로그인 창으로 이동
        }
    }

    //정보 수정 폼
    @GetMapping("/modifyForm/{user_id}")
    public String userUpdateForm(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        UserModifyForm userModifyForm = userService.touserModifyForm(user); // 인증을위한 userModifyForm값으로 변경
        model.addAttribute("userModifyForm", userModifyForm);
        return "/user/userModifyForm";
    }

    // 정보수정 실행
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{user_id}")
    public String userUpdate(Model model, @Valid UserModifyForm userModifyForm,
                             BindingResult bindingResult, Principal principal) {

        User user = userService.getUser(principal.getName());
        model.addAttribute("userModifyForm", userModifyForm);

        if (bindingResult.hasErrors()) {
            return "/user/userModifyForm";
        }
        //이메일 중복여부체크
        if (userService.checkEmailDuplication(user, userModifyForm)) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/userModifyForm";
        }
        //비밀번호, 비밀번호 확인 동일 체크
        if (!userModifyForm.getPwd1().equals(userModifyForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호와 비밀번호확인이 불일치합니다.");
            return "/user/userModifyForm";
        }
        try {
            userService.userModify(user, userModifyForm);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", "정보를 확인해주세요.");
            return "/user/userModifyForm";
        }
        return "redirect:/";
    }

    //  탈퇴하기
    @GetMapping("/delete/{user_id}")
    public String questionDelete(@PathVariable("user_id") String user_id,
                                 Principal principal) {
        User user = userService.getUser(user_id); //회원상세
        if (!user.getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        userService.userDelete(user);
        return "redirect:/user/logout";    //목록으로이동
    }
}