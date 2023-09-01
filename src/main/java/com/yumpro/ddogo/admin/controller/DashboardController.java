package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        //그래프
        HashMap<String,Integer> activeUserMap = new HashMap<>();
        HashMap<String,Integer> monthMap = new HashMap<>();
        int m=0;

        for(int i=0;i<12;i++){
            m =LocalDate.now().getMonthValue();
            int cnt = dashboardService.monthlyActiveUser(i);
            activeUserMap.put(i+"key",cnt);

            if(m-i>0){
                m=m-i;
            } else if (m==i) {
                m=12;
            } else {
                m=12-(i-m);
            }
            monthMap.put(i+"key",m);
            System.out.println(m);
        }

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
        //그래프
        model.addAttribute("activeUserMap",activeUserMap);
        model.addAttribute("monthMap",monthMap);
        //리스트
        model.addAttribute("hotplaceRanking",hotplaceRanking);

        //4.view
        return "admin/dashboard";
    }
}
