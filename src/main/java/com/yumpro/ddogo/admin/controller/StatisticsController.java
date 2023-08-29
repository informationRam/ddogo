package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    @GetMapping("/dashboard")
    public String getDashboard(){
        //1. 현재 총회원수
        int userTotal = statisticsService.getUserTotal();

        //2. 성별-나이에 따른 총 회원수
        int maleTeens = statisticsService.getUserTotalByAG("M",0,239);
        int maleTwenties = statisticsService.getUserTotalByAG("M",240,359);
        int maleThirties = statisticsService.getUserTotalByAG("M",360,479);
        int maleForties = statisticsService.getUserTotalByAG("M",480,599);
        int maleFifties = statisticsService.getUserTotalByAG("M",600,719);
        int maleSixties = statisticsService.getUserTotalByAG("M",720,10000);

        int femaleTeens = statisticsService.getUserTotalByAG("F",0,239);
        int femaleTwenties = statisticsService.getUserTotalByAG("F",240,359);
        int femaleThirties = statisticsService.getUserTotalByAG("F",360,479);
        int femaleForties = statisticsService.getUserTotalByAG("F",480,599);
        int femaleFifties = statisticsService.getUserTotalByAG("F",600,719);
        int femaleSixties = statisticsService.getUserTotalByAG("F",720,10000);

        //3. 연-월에 따른 총 회원수.....탈퇴 날짜가 있어야 계산 가능...또 디비 추가?...쉬벌
        LocalDate currentDate = LocalDate.now();
        int nowYear=currentDate.getYear();
        int nowMonth=currentDate.getMonthValue();
        int nowDate=currentDate.getDayOfMonth();








        return "admin/dashboard";
    }
}
