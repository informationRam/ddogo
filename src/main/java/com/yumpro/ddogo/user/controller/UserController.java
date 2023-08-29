package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(UserCreateForm userCreateForm, Model model){
        model.addAttribute("userCreateForm",userCreateForm);
        return "/user/joinForm";
    }

    //회원가입처리 후 로그인페이지로 이동
    @PostMapping("/join")
    public String userJoin(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model, LoginVaildation loginVaildation) {
        if (bindingResult.hasErrors()) {
            return "user/joinForm";
        }
        //아이디 중복여부체크
        if (userService.checkUserIdDuplication(userCreateForm.getUser_id())) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디입니다.");
            return "user/joinForm";
        }

        //이메일 중복여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/joinForm";
        }
        //비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호와 비밀번호확인이 불일치합니다.");
            return "user/joinForm";
        }else{
            userService.userJoin(userCreateForm);
            return "redirect:/user/login";
        }

    }

    //로그인 화면(get)
    @GetMapping("/login")
    public String loginForm(LoginVaildation loginVaildation) {
        return "/user/loginForm";
    }


    //로그인 처리

    @PostMapping("/login")
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }else {
            return "redirect:/";
        }

    }

   /* @PostMapping("/login")
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }else {
            userService.userlogin(loginVaildation.getUser_id(),loginVaildation.getPwd());
            return "redirect:/";
        }

    }*/


}
