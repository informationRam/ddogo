package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    //회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(User user, Model model){
        model.addAttribute("data","jointest");
        return "/user/joinForm";
    }
    //회원등록 폼
    @PostMapping("/join")
    public String userJoin(User user, Model model){
        model.addAttribute("data","jointest");
        return "redirect:/loginFrom";
    }

    @GetMapping("/loginFrom")
    public String loginForm(User user, Model model){
        model.addAttribute("data","jointest");
        return "/user/join";
    }


}
