package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.service.UserService;
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
    public String joinForm( User user, Model model){
        model.addAttribute("user",user);
        return "/user/joinForm";
    }

    //로그인 폼(post)
    @PostMapping("/join")
    public String userJoin(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "/user/joinForm";
        }else {
            user.setJoinDate(LocalDateTime.now());
            System.out.println("user:" + user);
            userService.userJoin(user);
            return "/user/loginForm";
        }
    }


    //로그인 폼(get)
    @GetMapping("/join")
    public String userJoin2() {
        return "/user/loginForm";
    }


    @PostMapping("/login")
    public String loginForm(User user, Model model){
        model.addAttribute("data","jointest");
        return "/user/join";
    }


}
