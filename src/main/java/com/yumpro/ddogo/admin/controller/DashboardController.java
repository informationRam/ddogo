package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/admin")
    public String dashBoard(Model model){
        //1.get param

        //2.business logic
        //카드
        int userTotal=dashboardService.getUserTotal();
        int recentUser=dashboardService.getRecentUser();
        int newPlaceCnt=dashboardService.newPlaceCnt();
        int hotplaceTotal=dashboardService.hotplaceTotal();
        double emoAvg=dashboardService.emoAvg();
        double RecentEmoAvg=dashboardService.RecentEmoAvg();
        int nowActiveUser=dashboardService.nowActiveUser();

        //그래프


        //랭크
        List<HashMap<String,Object>> hotplaceRanking=dashboardService.hotplaceRank();

        //3.model
        //카드
        model.addAttribute("userTotal",userTotal);
        model.addAttribute("recentUser",recentUser);
        model.addAttribute("hotplaceTotal",hotplaceTotal);
        model.addAttribute("newPlaceCnt",newPlaceCnt);
        model.addAttribute("emoAvg",emoAvg);
        model.addAttribute("RecentEmoAvg",RecentEmoAvg);
        model.addAttribute("nowActiveUser",nowActiveUser);

        //그래프
        //리스트
        model.addAttribute("hotplaceRanking",hotplaceRanking);

        //4.view
        return "admin/dashboard";
    }
}
