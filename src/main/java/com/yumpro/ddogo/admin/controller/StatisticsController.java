package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    @GetMapping("/dashboard")
    //총회원수 구하기
    public String userStatics(){
        Integer userTotal =statisticsService.getUserTotal();
        System.out.println(userTotal);
        return "";
    }
}
