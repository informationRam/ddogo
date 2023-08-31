package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/admin")
    public String showMap(){
        int i =dashboardService.getUserTotal();
        System.out.println(i);
        return "admin/dashboard";
    }
}
