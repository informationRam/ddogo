package com.yumpro.ddogo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class StatisticsController {
    @GetMapping("/dashboard")
    //총회원수 구하기
    public String userStatics(){
        return "";
    }
}
