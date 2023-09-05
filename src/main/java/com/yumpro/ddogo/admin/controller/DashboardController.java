package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.ActiveUserService;
import com.yumpro.ddogo.admin.service.DashboardService;
import com.yumpro.ddogo.common.entity.ActiveUser;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final ActiveUserService activeUserService;

    @GetMapping("/admin")
    public String dashBoardWithoutYear(Model model) throws NotFoundException {
        return dashBoard(Optional.empty(), model);
    }

    @GetMapping("/admin/{uYear}")
    public String dashBoard(@PathVariable Optional<Integer> uYear, Model model) throws NotFoundException {
        // 1. get param
        Integer year = uYear.orElse(LocalDate.now().getYear());  // 현재 연도를 기본값으로 설정

        //2.business logic
        //카드
        int userTotal=dashboardService.getUserTotal();
        int recentUser=dashboardService.getRecentUser();
        int newPlaceCnt=dashboardService.newPlaceCnt();
        int hotplaceTotal=dashboardService.hotplaceTotal();
        double emoAvg=dashboardService.emoAvg();
        double RecentEmoAvg=dashboardService.RecentEmoAvg();
        int nowActiveUser=dashboardService.nowActiveUser();

        double activePercent = ((double) nowActiveUser / userTotal) * 100;
        activePercent = Math.round(activePercent * 100.0) / 100.0;

        //그래프
        List<ActiveUser> activeUser=activeUserService.findByYear(year);
        List<HashMap<String, Object>> localHotplaceCnt=dashboardService.localHotplaceCnt();

        //리스트
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
        model.addAttribute("activeParcent",activePercent);

        //그래프
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("localHotplaceCnt",localHotplaceCnt);

        //리스트
        model.addAttribute("hotplaceRanking",hotplaceRanking);

        //4.view
        return "admin/dashboard";
    }
}
