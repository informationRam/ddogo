package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        int userTotal =dashboardService.getUserTotal();
        int recentUser=dashboardService.getRecentUser();
        List<HashMap<String,Object>> hotplaceRanking=dashboardService.hotplaceRank();

        //3.model
        model.addAttribute("userTotal",userTotal);
        model.addAttribute("recentUser",recentUser);
        model.addAttribute("hotplaceRanking",hotplaceRanking);

        //4.view
        return "admin/dashboard";
    }
}
