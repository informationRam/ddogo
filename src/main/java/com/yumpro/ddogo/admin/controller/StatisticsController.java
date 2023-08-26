package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class StatisticsController {
    StatisticsService statisticsService = new StatisticsService();
    @GetMapping("/dashboard")
    //총회원수 구하기
    public String userStatics(){
        int userTotal =statisticsService.getUserTotal();
        System.out.println(userTotal);
        return "";
    }
}
