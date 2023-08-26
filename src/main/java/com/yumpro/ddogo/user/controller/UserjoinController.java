package com.yumpro.ddogo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserjoinController {

    @GetMapping("/join")
    public String userAdd(Model model){
        model.addAttribute("data","jointest");
        return "/user/join";
    }
}
